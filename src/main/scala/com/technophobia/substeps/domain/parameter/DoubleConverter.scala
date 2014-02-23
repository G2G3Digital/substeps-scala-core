package com.technophobia.substeps.domain.parameter

import java.lang.Double
import collection.JavaConversions._
import com.technophobia.substeps.model.parameter.Converter

object DoubleConverter extends Converter[Double] {

  val converts: java.util.Set[Class[_]] = Set[Class[_]](Double.TYPE)
  def convert(value: String) = {

    if (value == null) {

      throw new NumberFormatException("null")
    }

    Double.valueOf(value)
  }
}
