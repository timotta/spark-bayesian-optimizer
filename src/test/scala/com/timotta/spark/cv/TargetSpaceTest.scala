package com.timotta.spark.cv

import java.io.File
import org.apache.spark.ml.BayesianPair
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.param.{ParamMap, ParamPair}
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.tuning.ParamGridBuilder

class TargetSpaceTest extends BaseTest {

  it should "random sample" in {

    var estimator = new LinearRegression()

    val paramGrid:Array[Array[BayesianPair[_]]] = Array(
      Array(new BayesianPair(estimator.maxIter, 1), new BayesianPair(estimator.regParam, 0.1)),
      Array(new BayesianPair(estimator.maxIter, 10), new BayesianPair(estimator.regParam, 0.2))
    )

    val ts = new TargetSpace(paramGrid, 1)

    val expected = ParamMap(ParamPair(estimator.maxIter, 5), ParamPair(estimator.regParam, 0.17203245))
    val result = ts.randomSample()

    assert(expected == result)

  }

}
