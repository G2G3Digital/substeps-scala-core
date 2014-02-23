package com.technophobia.substeps.domain.parameter

import java.lang.Long
import collection.JavaConversions._
import com.technophobia.substeps.model.parameter.Converter

object LongConverter extends Converter[Long] {

  val converts: java.util.Set[Class[_]] = Set[Class[_]](classOf[Long])
  def convert(value: String) = Long.valueOf(value)
}
