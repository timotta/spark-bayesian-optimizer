package com.timotta.spark.cv

import org.apache.spark.ml.param.{ParamMap, ParamPair}
import org.apache.spark.ml.BayesianParam
import scala.util.Random
import scala.collection.mutable.MutableList

class TargetSpace(func: (Array[Double]) => Double, bounds: Array[(Double, Double)], random: UniformRandom) {

  private[cv] val xs = MutableList[Array[Double]]()
  private[cv] val ys = MutableList[Double]()
  
  def this(func: (Array[Double]) => Double, bounds: Array[(Double, Double)], random: Random) = {
    this(func, bounds, new UniformRandom(random))
  }
  
  def randomSample(): Array[Double] = {
    bounds.map { case (a, b) => random.inRange(a, b) }
  }

  def probe(x: Array[Double]): Double = {
    val y = func(x)
    xs.+=(x)
    ys.+=(y)
    y
  }
}


