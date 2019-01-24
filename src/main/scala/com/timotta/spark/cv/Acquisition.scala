package com.timotta.spark.cv

import breeze.linalg.DenseMatrix
import breeze.optimize.LBFGSB
import breeze.linalg.DenseVector
import breeze.optimize.ApproximateGradientFunction

class Acquisition(bounds: Array[Array[Double]], kappa: Double = 2.576, random: UniformRandom = new UniformRandom()) {

  def max(gp: GaussianProcessModel, warmup: Int = 100000, iters: Int = 250) {
    val xTries = randomTries(warmup)

    val ys = gpAndUcb(gp, xTries)

    val (xMax, yMax) = xTries.zip(ys).sortBy(_._2).last
    val xSeeds = randomTries(iters)

    maximize(gp, xSeeds(0))

    """
    for x_try in x_seeds:
        # Find the minimum of minus the acquisition function
        res = minimize(lambda x: -ac(x.reshape(1, -1), gp=gp, y_max=y_max),
                       x_try.reshape(1, -1),
                       bounds=bounds,
                       method="L-BFGS-B")

        # See if success
        if not res.success:
            continue

        # Store it if better than previous minimum(maximum).
        if max_acq is None or -res.fun[0] >= max_acq:
            x_max = res.x
            max_acq = -res.fun[0]
"""

  }

  def maximize(gp: GaussianProcessModel, xSeed: Array[Double]) {
    val (lower, upper) = boundsAsTuple
    val minimizer = new LBFGSB(DenseVector(lower), DenseVector(upper))
    val fn = new ApproximateGradientFunction(
      (d: DenseVector[Double]) => -gpAndUcb(gp, Array(d.toArray)).head)
    minimizer.minimize(fn, DenseVector(xSeed)).toArray
  }

  def boundsAsTuple: (Array[Double], Array[Double]) = {
    val y = bounds.map(a => (a(0), a(1)))
    val z = y.unzip
    (z._1.toArray, z._2.toArray)
  }

  def randomTries(n: Int) = {
    0.until(n).map { r =>
      bounds.map { bound =>
        random.inRange(bound(0), bound(1))
      }.toArray
    }.toArray
  }

  def gpAndUcb(gp: GaussianProcessModel, xTries: Array[Array[Double]]) = {
    val (mean, std) = gp.predict(xTries)
    ucb(mean, std)
  }

  def ucb(mean: Array[Double], std: Array[Double]) = {
    mean.zip(std).map {
      case (m, s) =>
        m + kappa * s
    }
  }


}