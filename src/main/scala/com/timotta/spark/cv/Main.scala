package com.timotta.spark.cv

object Main {
  def main(args: Array[String]): Unit = {
    println("!!!!!!!!!!!")
    
    println( new BayesianOptimizer().estimator )
    println( new BayesianOptimizer().getEstimatorParamMaps )
    
    println("!!!!!!!!!!!")
    
  }
}