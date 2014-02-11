package com.technophobia.substeps.model.parameter

import java.lang.Double

object DoubleConverter extends Converter {

  val converts = Double.TYPE
  def convert(value: String) = {

    if (value == null) {

      throw new NumberFormatException("null")
    }

    Double.valueOf(value)
  }
}
