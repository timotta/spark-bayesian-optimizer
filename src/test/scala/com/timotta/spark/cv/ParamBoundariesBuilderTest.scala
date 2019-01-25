package com.timotta.spark.cv

import org.apache.spark.ml.ParamBoundariesBuilder
import org.apache.spark.ml.regression.LinearRegression

class ParamBoundariesBuilderTest extends BaseTest {

  it should "ParamBoundariesBuilder" in  {

    val pbb = new ParamBoundariesBuilder()

    val estimator = new LinearRegression()


    val bounds = pbb
      .addGrid(estimator.epsilon, Array(0.2,0.3))
      .addGrid(estimator.maxIter, Array(2,10)).build()
    bounds.foreach(println)

  }

}
