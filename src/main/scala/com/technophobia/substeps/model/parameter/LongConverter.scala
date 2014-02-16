package com.technophobia.substeps.model.parameter

import java.lang.Long
import collection.JavaConversions._

object LongConverter extends Converter[Long] {

  val converts: java.util.Set[Class[_]] = Set[Class[_]](classOf[Long])
  def convert(value: String) = Long.valueOf(value)
}
