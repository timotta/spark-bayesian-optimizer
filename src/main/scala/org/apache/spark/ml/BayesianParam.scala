package org.apache.spark.ml

import org.apache.spark.ml.param.{Param, ParamPair}

case class BayesianParam[T](other: Param[T])
  extends Param[T](other.parent, other.name, other.doc, other.isValid) {
}
