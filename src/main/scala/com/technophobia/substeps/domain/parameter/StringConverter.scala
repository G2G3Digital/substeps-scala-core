package com.technophobia.substeps.domain.parameter

import collection.JavaConversions._
import com.technophobia.substeps.model.parameter.Converter

object StringConverter extends Converter[String] {

  val converts: java.util.Set[Class[_]] = Set[Class[_]](classOf[String])
  def convert(value: String) = value
}
