package com.timotta.spark.cv

import org.apache.spark.ml.param.{ParamMap, ParamPair}
import org.apache.spark.ml.{BayesianParam, ParamBoundariesBuilder}
import scala.util.Random
import scala.collection.mutable.MutableList

class TargetSpace(func: (Array[Double]) => Double, bounds: Array[ParamMap], random: UniformRandom) {

  private[cv] val xs = MutableList[Array[Double]]()
  private[cv] val ys = MutableList[Double]()

  lazy private val boundsArray:Array[(Double,Double)] = {
    val lower = bounds.head.toSeq.map{
      x => fromAnyValueToDouble(x.value)}.toArray

    val upper = bounds.last.toSeq.map{
      x => fromAnyValueToDouble(x.value)}.toArray
    val ranges = lower.zip(upper)
    ranges.foreach(println)
    ranges
  }


  def fromAnyValueToDouble(v:Any):Double = {
    v match {
      case v:Double => v
      case v:Int => v.toDouble
    }
  }
  def this(func: (Array[Double]) => Double, bounds: Array[ParamMap], random: Random) = {
    this(func, bounds, new UniformRandom(random))
  }

  
  def randomSample(): Array[Double] = {
    boundsArray.map { case (a, b) => random.inRange(a, b) }
  }

  def probe(x: Array[Double]): Double = {
    val y = func(x)
    xs.+=(x)
    ys.+=(y)
    y
  }
}


