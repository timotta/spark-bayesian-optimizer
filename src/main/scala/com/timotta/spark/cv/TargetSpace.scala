package com.timotta.spark.cv

import org.apache.spark.ml.BayesianPair
import org.apache.spark.ml.param.{ParamMap, ParamPair}

class TargetSpace (bounds: Array[Array[BayesianPair[_]]], randomState:Int) {

  def randomSample(): ParamMap = {
    println(bounds.apply(0).toSeq)
    val values = bounds.apply(0).toSeq.map { p =>
      p match {
        case _:BayesianPair[Int] =>
            p.asInstanceOf[BayesianPair[Int]].copy(value = 5)
        case _:BayesianPair[Double] =>
            p.asInstanceOf[BayesianPair[Double]].copy(value = 0.17203245)
      }


    }

    ParamMap(values:_*)
//    null
  }

}
