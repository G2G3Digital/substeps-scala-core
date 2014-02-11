package com.technophobia.substeps.model.parameter

import java.lang.Integer

object IntegerConverter extends Converter {

  val converts = classOf[Int]
  def convert(value: String) = Integer.valueOf(value)
}
