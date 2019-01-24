package org.apache.spark.ml

import org.apache.spark.ml.param.{Param, ParamPair}

class BayesianPair[T](other: Param[T], value: T)
  extends ParamPair[T](other, value) {
}
