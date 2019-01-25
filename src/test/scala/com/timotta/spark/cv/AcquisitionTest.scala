package com.timotta.spark.cv

class AcquisitionTest extends BaseTest {

  it should "randomTries" in {
    val bounds = Array(
      Array(1.0, 5.0),
      Array(20.0, 30.0),
      Array(0.0, 0.1),
      Array(8.0, 9.0),
      Array(-0.2, -0.1))

    val expected = Array(
      Array(3.9235127628131634, 24.100808114922017, 0.02077148413097171, 8.332717055959511, -0.10322440905758794),
      Array(1.0244687290630452, 29.637047970232075, 0.09398653887819099, 8.947194917663193, -0.10629178511040305),
      Array(2.5886973687388224, 23.4751802920311, 0.029405703200403678, 8.506483627326235, -0.18840329119673424))

    val result = new Acquisition(bounds, random = new UniformRandom(1)).randomTries(3)

    assert(~=(expected, result, 0.0001))
  }

  it should "ucb" in {
    val means = Array(2D, 3D, 5D)
    val stds = Array(7D, 11D, 13D)

    val result = new Acquisition(Array(), kappa = 17).ucb(means, stds)

    val expected = Array(121D, 190D, 226D)

    assert(~=(expected, result, 0.0001))
  }

  it should "gpAndUcb" in {
    val gp = new GaussianProcessModel(null) {
      override def predict(v: Array[Array[Double]]) = {
        (Array(v(0).sum), Array(0.001))
      }
    }

    val xTries = Array(Array(8D, 5D, 4D))
    val result = new Acquisition(Array(
      Array(2D, 9D),
      Array(1D, 10D),
      Array(3.9, 16D)), kappa = 17).gpAndUcb(gp, xTries)

    assert(~=(Array(17.017), result, 0.001))
  }

  it should "maximize" in {
    val gp = new GaussianProcessModel(null) {
      override def predict(v: Array[Array[Double]]) = {
        (Array(-math.pow(v(0).sum, 2)), Array(0.001))
      }
    }

    val xTry = Array(8D, 5D, 4D)
    val result = new Acquisition(Array(
      Array(-2D, 20D),
      Array(-1D, 10D),
      Array(-3.9, 16D)), kappa = 0.01).maximize(gp, xTry)

    val expected = Array(0.8870271969437802, 0.732216318166268, -1.6192485144144135)

    assert(~=(expected, result, 0.001))
  }

  it should "maxResult" in {
    val steps = Array(
      Some((Array(1D, 2D), 9D)),
      Some((Array(3D, 4D), 10D)),
      None,
      Some((Array(5D, 6D), 8D)))

    val default = (Array(7D, 8D), 7D)

    val result = new Acquisition(null).maxResult(steps, default)

    val expected = (Array(3D, 4D), 10D)

    assert(~=(expected._1, result._1, 0.001))
    assert(~=(expected._2, result._2, 0.001))
  }
  
  it should "maxResult when default bigger" in {
    val steps = Array[Option[(Array[Double], Double)]](
      None,
      None,
      None,
      None)

    val default = (Array(7D, 8D), 12D)

    val result = new Acquisition(null).maxResult(steps, default)

    val expected = (Array(7D, 8D), 12D)

    assert(~=(expected._1, result._1, 0.001))
    assert(~=(expected._2, result._2, 0.001))
  }

}