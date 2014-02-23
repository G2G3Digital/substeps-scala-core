package com.technophobia.substeps.domain.parameter

import java.lang.Integer
import collection.JavaConversions._
import com.technophobia.substeps.model.parameter.Converter


object IntegerConverter extends Converter[Integer] {

  val converts: java.util.Set[Class[_]] = Set[Class[_]](classOf[Int])
  def convert(value: String) = Integer.valueOf(value)
}
