package com.technophobia.substeps.model.parameter

import collection.JavaConversions._

object StringConverter extends Converter[String] {

  val converts: java.util.Set[Class[_]] = Set[Class[_]](classOf[String])
  def convert(value: String) = value
}
