package com.timotta.spark.cv

import breeze.linalg.{DenseMatrix, DenseVector}
import dk.gp.cov.CovSEiso
import dk.gp.gpr.{GprModel, gpr, gprPredict}
import scala.math.log

class GaussianProcess() {

  def fit(x: Array[Array[Double]], y:Array[Double]): GaussianProcessModel = {
    val xMatrix = DenseMatrix(x:_*)
    val yVector = DenseVector(y)

    val covFunc = CovSEiso()
    val covFuncParams = DenseVector(log(1D), log(1D))
    val noiseLogStd = log(0.1)

    val model = gpr(xMatrix, yVector, covFunc, covFuncParams, noiseLogStd)


    new GaussianProcessModel(model)
  }

}

class GaussianProcessModel(model: GprModel){

  def predict(x: Array[Array[Double]]):(Array[Double],Array[Double]) = {
    val xMatrix = DenseMatrix(x:_*)
    val result = gprPredict(xMatrix, model)

    val mean = result.apply(::,0).toArray
    val standardDeviation = result.apply(::,1).toArray

    (mean, standardDeviation)
  }
}
