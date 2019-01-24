package com.timotta.spark.cv

import scala.util.Random

class UniformRandom(random: Random) {
  
  def this(seed: Int) = {
    this(new Random(seed))
  }
  
  def this() = {
    this(new Random())
  }
  
  def inRange(start: Double, end: Double): Double = {
    random.nextDouble() * (end - start) + start
  }
  
  def inRange(start: Double, end: Double, size:Int):Array[Double] = {
    (0 until size).map{
      _ => inRange(start, end)
    }.toArray
  }
}