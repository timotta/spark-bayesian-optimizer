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

  //TODO: validate if the lowerBoudarie < upperBoundarie
  /**
    * Buid a Array with two positions: First is the lower boundarie of all params; Second is the upper boundarie of all params;
    * @return
    */
  override def build():Array[ParamMap] = {

    var lowerMap = new ParamMap()
    var upperMap = new ParamMap()
    boundariesGrid.foreach{
      case (param:IntParam, v:mutable.WrappedArray[Int] ) =>
        lowerMap.put(param,v.head)
        upperMap.put(param,v.last)

      case (param:DoubleParam, v:mutable.WrappedArray[Double] ) =>
        lowerMap.put[Double](param,v.head)
        upperMap.put[Double](param,v.last)
    }

    Array(lowerMap,upperMap)
  }
}
