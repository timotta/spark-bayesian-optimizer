package org.apache.spark.ml

import org.apache.spark.ml.util.MLWritable
import org.apache.spark.ml.util.MLWriter

trait MLWritableProxy extends MLWritable {
  override def write: MLWriter = writeProxy

  def writeProxy: MLWriterProxy
}

trait MLWriterProxy extends MLWriter {

}