package dk.gp.gpr

import breeze.linalg.DenseMatrix
import breeze.linalg.DenseVector
import dk.gp.cov.CovFunc
import scala.math._
import breeze.linalg.inv
import breeze.linalg.cholesky
import dk.gp.math.invchol

case class GprModel(x: DenseMatrix[Double], y: DenseVector[Double], covFunc: CovFunc, covFuncParams: DenseVector[Double], noiseLogStdDev: Double, meanFunc: (DenseMatrix[Double]) => DenseVector[Double]) {

  def predict(x2: DenseMatrix[Double]): DenseMatrix[Double] = {
    
    val a = covFunc.cov(x2, x2, covFuncParams)
    val b = a.+(exp(2 * noiseLogStdDev))
    
    
    val kXX = b * DenseMatrix.eye[Double](x2.rows) + DenseMatrix.eye[Double](x2.rows).*(1e-7)
    kXX
  }
  
  def calcKXX(): DenseMatrix[Double] = {
    predict(x)
  }

  def calcKXXInv(kXX: DenseMatrix[Double]): DenseMatrix[Double] = {
    val kXXInv = invchol(cholesky(kXX).t)
    kXXInv
  }
}

object GprModel {

  def apply(x: DenseMatrix[Double], y: DenseVector[Double], covFunc: CovFunc, covFuncParams: DenseVector[Double], noiseLogStdDev: Double, mean: Double = 0d): GprModel = {

    val meanFunc = (x: DenseMatrix[Double]) => DenseVector.zeros[Double](x.rows) + mean
    new GprModel(x, y, covFunc, covFuncParams, noiseLogStdDev, meanFunc)
  }

}