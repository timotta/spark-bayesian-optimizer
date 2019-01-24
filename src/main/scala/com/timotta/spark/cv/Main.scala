package com.timotta.spark.cv

import dk.gp.gpr.gpr
import dk.gp.cov.CovSEiso
import breeze.linalg.DenseVector
import scala.math.log
import breeze.linalg.DenseMatrix

object Main {
  def main(args: Array[String]): Unit = {
    println("!!!!!!!!!!!")
    
    val covFunc = CovSEiso()
    val covFuncParams = DenseVector(log(1D), log(1D))
    val noiseLogStd = log(0.1)
    
    
    val x = DenseMatrix(
        (1.0,2.0,3.0),
        (4.0,5.0,6.0)
    )
    
    val y = DenseVector(5.0, 4.0)
    
    println(x)
    
    println(x.cols)
    
    val z = gpr(x, y, covFunc, covFuncParams, noiseLogStd)

    
//    println( z.covFuncParams )
//    
    println( z.predict(DenseMatrix(
      (3.0, 3.0, 3.1),
      (3.5, 4.0, 3.3)
    )) )
    
    println("!!!!!!!!!!!")
    
  }
}