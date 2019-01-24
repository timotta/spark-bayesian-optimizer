package org.apache.spark.ml

import org.apache.spark.ml.tuning.ValidatorParams
import org.apache.spark.SparkContext
import org.json4s.JObject

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

}