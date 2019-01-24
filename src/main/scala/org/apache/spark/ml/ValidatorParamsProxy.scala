package org.apache.spark.ml

import org.apache.spark.ml.tuning.ValidatorParams
import org.apache.spark.SparkContext
import org.json4s.JObject
import org.apache.spark.ml.evaluation.Evaluator
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.util.DefaultParamsReader.Metadata

trait ValidatorParamsProxy extends ValidatorParams

object ValidatorParamsProxy {

  def validateParams(instance: ValidatorParams): Unit = {
    ValidatorParams.validateParams(instance)
  }

  def saveImpl(
    path: String,
    instance: ValidatorParams,
    sc: SparkContext,
    extraMetadata: Option[JObject] = None): Unit = {
    ValidatorParams.saveImpl(path, instance, sc, extraMetadata)
  }

  def loadImpl[M <: Model[M]](
      path: String,
      sc: SparkContext,
      expectedClassName: String): (Metadata, Estimator[M], Evaluator, Array[ParamMap]) = {
    ValidatorParams.loadImpl(path, sc, expectedClassName)
  }
}