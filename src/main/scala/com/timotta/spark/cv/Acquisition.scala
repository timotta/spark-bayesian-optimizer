package com.timotta.spark.cv

import breeze.linalg.DenseMatrix
import breeze.optimize.LBFGSB
class Acquisition(bounds: Array[Array[Double]], kappa: Double = 2.576, random: UniformRandom = new UniformRandom()) {

  def max(gp: GaussianProcessModel, warmup: Int = 100000, iters: Int = 250) {
    val xTries = randomTries(warmup)

    val (mean, std) = gp.predict(xTries)
    val ys = ucb(mean, std)

    val (xMax, yMax) = xTries.zip(ys).sortBy(_._2).last
    val xSeeds = randomTries(iters)


  }

  def randomTries(n: Int) = {
    0.until(n).map { r =>
      bounds.map { bound =>
        random.inRange(bound(0), bound(1))
      }.toArray
    }.toArray
  }

  def ucb(mean: Array[Double], std: Array[Double]) = {
    mean.zip(std).map {
      case (m, s) =>
        m + kappa * s
    }
  }

  def minimize(): Unit ={

  }

}