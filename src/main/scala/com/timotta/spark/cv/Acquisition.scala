package com.timotta.spark.cv

import breeze.linalg.DenseMatrix
import breeze.optimize.LBFGSB
import breeze.linalg.DenseVector
import breeze.optimize.ApproximateGradientFunction

class Acquisition(bounds: Array[Array[Double]], kappa: Double = 2.576, random: UniformRandom = new UniformRandom()) {

  def max(gp: GaussianProcessModel, warmup: Int = 100000, iters: Int = 250) = {
    val xTries = randomTries(warmup)

    val ys = gpAndUcb(gp, xTries)

    val (xMax, yMax) = xTries.zip(ys).sortBy(_._2).last
    val xSeeds = randomTries(iters)

    val steps = xSeeds.map { xSeed =>
      val newX = maximize(gp, xSeed)
      val newY = gpAndUcb(gp, Array(newX))(0)
      if (newY >= yMax) {
        Some((newX, newY))
      } else {
        None
      }
    }
    
    val result = maxResult(steps, (xMax, yMax))
 
    (clip(result._1), result._2)
  }

  def clip(result: Array[Double]) = {
    val params = result.zip(bounds).map {
        case (param, Array(lower, upper)) =>
          if (param < lower) { lower }
          else if (param > upper) { upper }
          else { param }
    }
    params
  }

  def maxResult(steps: Array[Option[(Array[Double], Double)]], default: (Array[Double], Double)): (Array[Double], Double) = {
    steps.collect { case Some(a) => a }
      .sortBy(_._2)
      .lastOption.getOrElse(default)
  }

  def maximize(gp: GaussianProcessModel, xSeed: Array[Double]) = {
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