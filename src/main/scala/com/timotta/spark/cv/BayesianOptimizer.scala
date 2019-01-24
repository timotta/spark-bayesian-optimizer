package com.timotta.spark.cv

import org.apache.spark.ml.Estimator
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.param.shared.HasCollectSubModels
import org.apache.spark.ml.tuning.CrossValidatorModel
import org.apache.spark.ml.util.Identifiable
import org.apache.spark.ml.util.MLWritable
import org.apache.spark.ml.util.MLWriter
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.types.StructType

class BayesianOptimizer(override val uid: String) extends Estimator[CrossValidatorModel]
  with HasCollectSubModels
  with MLWritable {

  def this() = this(Identifiable.randomUID("cv"))

  override def fit(dataset: Dataset[_]): CrossValidatorModel = {
    null
  }

  override def copy(extra: ParamMap): Estimator[CrossValidatorModel] = {
    null
  }

  override def transformSchema(schema: StructType): StructType = schema

  override def write: MLWriter = new BayesianOptimizer.Writer(this)
}

object BayesianOptimizer {
  private[BayesianOptimizer] class Writer(instance: BayesianOptimizer) extends MLWriter {
    //ValidatorParams.validateParams(instance)
    override protected def saveImpl(path: String): Unit = {
      //ValidatorParams.saveImpl(path, instance, sc)
    }
  }
}