package org.apache.spark.ml

import org.apache.spark.ml.tuning.ValidatorParams
import org.apache.spark.ml.param.IntParam
import org.apache.spark.ml.param.ParamValidators

trait BayesianOptimizerParams extends ValidatorParams {
  /**
   * Param for number of folds for cross validation.  Must be &gt;= 2.
   * Default: 3
   *
   * @group param
   */
  val numFolds: IntParam = new IntParam(this, "numFolds",
    "number of folds for cross validation (>= 2)", ParamValidators.gtEq(2))

  /** @group getParam */
  def getNumFolds: Int = $(numFolds)

  setDefault(numFolds -> 3)
}