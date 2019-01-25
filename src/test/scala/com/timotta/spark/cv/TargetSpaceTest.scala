package com.timotta.spark.cv

import java.io.File
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.param.{ParamMap, ParamPair}
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.tuning.ParamGridBuilder
import org.apache.spark.ml.{BayesianParam, ParamBoundariesBuilder}
import scala.util.Random

class TargetSpaceTest extends BaseTest {

  it should "probe return result of targete" in {
    val func = (a: Array[Double]) => 10.0
    val ts = new TargetSpace(func, null, new Random(1))
    assert(ts.probe(Array(0.1, 9.0)) == 10.0)
  }

  it should "probe accumulate result" in {
    val func = (a: Array[Double]) => 10.0

    val ts = new TargetSpace(func, null, new Random(1))

    ts.probe(Array(0.1, 9.0))
    ts.probe(Array(0.2, 8.0))

    assert(~=(ts.ys.toArray, Array(10.0, 10.0), 0.0001))
    assert(~=(ts.xs.toArray, Array(Array(0.1, 9.0), Array(0.2, 8.0)), 0.0001))
  }

  it should "random sample" in {
    val estimator = new LinearRegression()

    val bounds = new ParamBoundariesBuilder()
      .addGrid(estimator.regParam, Array(0.1D,0.2D))
      .addGrid(estimator.maxIter, Array(1,10))
      .build()

    val ts = new TargetSpace(null, bounds, new Random(1))

    val result = ts.randomSample()
    val expected = Array(7.57790,0.14100808)

    assert(~=(result, expected, 0.0001))
  }

}
