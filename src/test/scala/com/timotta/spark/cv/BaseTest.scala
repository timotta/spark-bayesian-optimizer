package com.timotta.spark.cv

import java.util
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec}
import scala.reflect.ClassTag
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll

abstract class BaseTest extends FlatSpec with BeforeAndAfter with MockitoSugar {
  def when[T]: (T) => OngoingStubbing[T] = org.mockito.Mockito.when[T]

  def doReturn(v: Any) = org.mockito.Mockito.doReturn(v, List[Object](): _*)

  def mockSerializable[T <: AnyRef](implicit classTag: ClassTag[T]): T = mock[T](Mockito.withSettings().serializable())

  def setEnv(key: String, value: String): Unit = {
    val map: util.Map[String, String] = getEnvironmentVariables
    map.put(key, value)
  }

  def unsetEnv(key: String): Unit = {
    val map: util.Map[String, String] = getEnvironmentVariables
    map.remove(key)
  }

  private def getEnvironmentVariables = {
    val field = System.getenv().getClass.getDeclaredField("m")
    field.setAccessible(true)
    val map = field.get(System.getenv()).asInstanceOf[util.Map[String, String]]
    map
  }

  def ~=(x: Double, y: Double, precision: Double): Boolean = {
    if ((x - y).abs < precision) true else false
  }

  def ~=(x: Array[Double], y: Array[Double], precision: Double): Boolean = {
    x.zip(y).foreach {
      case (a, b) =>
        if (! ~=(a, b, 0.0001))
          return false
    }
    true
  }

  def ~=(x: Array[Array[Double]], y: Array[Array[Double]], precision: Double): Boolean = {
    x.zip(y).foreach {
      case (a, b) =>
        if (! ~=(a, b, 0.0001))
          return false
    }
    true
  }
}