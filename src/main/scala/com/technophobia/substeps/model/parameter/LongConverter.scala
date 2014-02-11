package com.technophobia.substeps.model.parameter

import java.lang.Long

object LongConverter extends Converter {

  val converts = classOf[Long]
  def convert(value: String) = Long.valueOf(value)
}
