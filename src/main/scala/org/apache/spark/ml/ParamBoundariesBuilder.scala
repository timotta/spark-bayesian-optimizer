package org.apache.spark.ml

import org.apache.spark.ml.param._
import org.apache.spark.ml.tuning.ParamGridBuilder
import scala.collection.mutable

class ParamBoundariesBuilder() extends ParamGridBuilder {

  private val boundariesGrid = mutable.Map.empty[Param[_], Iterable[_]]

  override def addGrid[T](param: Param[T], values: Iterable[T]): this.type = {
    boundariesGrid.put(param, values)
    this
  }

  override def build():Array[ParamMap] = {

    var lowerMap = new ParamMap()
    var upperMap = new ParamMap()
    boundariesGrid.foreach{
      case (param:Param[Int], v:mutable.WrappedArray[Int] ) =>
        println("INT")
        println(param)
        println(v)
        lowerMap.put(param,v.head)

      case (param:Param[Double], v:mutable.WrappedArray[Double] ) =>
        println("Doubel")

        println(param)
        println(v)
      }


    Array(lowerMap,upperMap)
  }
}