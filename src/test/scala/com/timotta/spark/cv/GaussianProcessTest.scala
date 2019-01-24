package com.timotta.spark.cv

import breeze.linalg.{DenseMatrix, DenseVector}

class GaussianProcessTest extends BaseTest {

  it should "fit and predict" in {
    val x = Array(
      Array(1.0,2.0,3.0), // exemplo 1
      Array(4.0,5.0,6.0) //exemplo 2
    )

    val y = Array(10.0, 1.0)

    val xTest = Array(
      Array(1.1, 2.1, 3.1),
      Array(3.9, 4.9, 5.9),
      Array(2.5, 3.5, 4.5)
    )

    val expected =
      (
        Array(9.957188795647246,1.2261371486885317, 6.122516131627145), // media
        Array(0.17548642085394306, 0.17548642085388622, 13.01476953036775) //stde

        )


    val gp = new GaussianProcess()
    val result = gp.fit(x,y).predict(xTest)

    assert(~=(expected._1,result._1,0.001))
    assert(~=(expected._2,result._2,0.001))


    //      Array((1.0, 10.0), (0.1, 0.2))
//    val ts = new TargetSpace(null, params, new Random(1))
//
//    val result = ts.randomSample()
//
//    val expected = Array(7.57790, 0.14100808)
//
//    assert(~=(result, expected, 0.0001))

  }

}
