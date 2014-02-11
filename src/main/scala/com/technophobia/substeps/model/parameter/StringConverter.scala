package com.technophobia.substeps.model.parameter

object StringConverter extends Converter {

  val converts = classOf[String]
  def convert(value: String) = value
}
