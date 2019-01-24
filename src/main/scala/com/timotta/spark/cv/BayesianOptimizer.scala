package com.timotta.spark.cv

import org.apache.spark.ml.Estimator
import org.apache.spark.ml.ValidatorParamsProxy;
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.tuning.CrossValidatorModel
import org.apache.spark.ml.util.Identifiable
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.types.StructType
import org.apache.spark.ml.util.MLWriter
import org.apache.spark.ml.util.MLWritable
import org.apache.spark.ml.util.MLReader
import org.apache.spark.ml.util.MLReadable
import org.apache.spark.ml.BayesianOptimizerParams
import org.apache.spark.ml.evaluation.Evaluator

class BayesianOptimizer(val uid: String) 
  extends Estimator[CrossValidatorModel]
  with ValidatorParamsProxy
//  with CrossValidatorParams with HasParallelism with HasCollectSubModels
//  with MLWritable with Logging {
  with MLWritable
  {

  //TODO put in a params class
  def setEstimator(value: Estimator[_]): this.type = set(estimator, value)
  def setEstimatorParamMaps(value: Array[ParamMap]): this.type = set(estimatorParamMaps, value)
  def setEvaluator(value: Evaluator): this.type = set(evaluator, value)
  def setSeed(value: Long): this.type = set(seed, value)
  //def setNumFolds(value: Int): this.type = set(numFolds, value)  
  
  def this() = this(Identifiable.randomUID("cv"))

  override def fit(dataset: Dataset[_]): CrossValidatorModel = {
    null
  }

  override def copy(extra: ParamMap): Estimator[CrossValidatorModel] = {
    this
  }

  override def transformSchema(schema: StructType): StructType = schema

  override def write: MLWriter = new BayesianOptimizer.Writer(this)
}

object BayesianOptimizer extends MLReadable[BayesianOptimizer] {
  
  override def read: MLReader[BayesianOptimizer] = new Reader

  private[BayesianOptimizer] class Writer(instance: BayesianOptimizer) extends MLWriter {
    ValidatorParamsProxy.validateParams(instance)
    override protected def saveImpl(path: String): Unit = {
      ValidatorParamsProxy.saveImpl(path, instance, sc)
    }
  }

  private class Reader extends MLReader[BayesianOptimizer] {

    /** Checked against metadata when loading model */
    private val className = classOf[BayesianOptimizer].getName

    override def load(path: String): BayesianOptimizer = {
      //      implicit val format = DefaultFormats
      //
      //      val (metadata, estimator, evaluator, estimatorParamMaps) =
      //        ValidatorParams.loadImpl(path, sc, className)
      //      val cv = new CrossValidator(metadata.uid)
      //        .setEstimator(estimator)
      //        .setEvaluator(evaluator)
      //        .setEstimatorParamMaps(estimatorParamMaps)
      //      metadata.getAndSetParams(cv, skipParams = Option(List("estimatorParamMaps")))
      //      cv
      null
    }
  }
}