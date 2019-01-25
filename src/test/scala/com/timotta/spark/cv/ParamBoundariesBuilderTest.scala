package com.timotta.spark.cv

import org.apache.spark.ml.ParamBoundariesBuilder
import org.apache.spark.ml.regression.LinearRegression

class ParamBoundariesBuilderTest extends BaseTest {

  it should "ParamBoundariesBuilder" in  {

    val estimator = new LinearRegression()

    val bounds = new ParamBoundariesBuilder()
      .addGrid(estimator.regParam, Array(0.2D,0.3D))
      .addGrid(estimator.maxIter, Array(2,10))
      .build()

    val lowerMap = bounds.head
    val uppermap = bounds.last

    val lowerEpsilon = lowerMap.get(estimator.regParam)
    val upperEpsilon = uppermap.get(estimator.regParam)

    val lowerMaxIter = lowerMap.get(estimator.maxIter)
    val upperMaxIter = uppermap.get(estimator.maxIter)

    assert(Some(0.2)==lowerEpsilon)
    assert(Some(0.3)==upperEpsilon)

    assert(Some(2)==lowerMaxIter)
    assert(Some(10)==upperMaxIter)

  }

}
