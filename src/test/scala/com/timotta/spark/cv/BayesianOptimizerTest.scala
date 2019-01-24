package com.timotta.spark.cv

import org.apache.spark.sql.SparkSession
import org.scalatest.BeforeAndAfterAll
import java.io.File
import org.apache.spark.ml.tuning.ParamGridBuilder
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.ml.regression.LinearRegression

class BayesianOptimizerTest extends BaseTest with BeforeAndAfterAll {

  var spark: SparkSession = _

  override def beforeAll() {
    spark = SparkSession.builder().master("local").appName("test").getOrCreate()
  }

  override def afterAll() {
    spark.close()
  }

  it should "write" in {
    val path = "/tmp/bayes_" + System.currentTimeMillis()
    val paramGrid = new ParamGridBuilder().build()
    val opt1 = new BayesianOptimizer()
      .setEstimatorParamMaps(paramGrid)
      .setEvaluator(new BinaryClassificationEvaluator)
      .setEstimator(new LinearRegression)
    opt1.save(path)
    assert(new File(path).exists())
  }

  it should "read and write" in {
    val path = "/tmp/bayes_" + System.currentTimeMillis()
    val paramGrid = new ParamGridBuilder().build()
    val opt1 = new BayesianOptimizer()
      .setEstimatorParamMaps(paramGrid)
      .setEvaluator(new BinaryClassificationEvaluator)
      .setEstimator(new LinearRegression)
    opt1.save(path)
    val opt2 = BayesianOptimizer.load(path)
    assert(opt1.uid == opt2.uid)
  }

}