package com.technophobia.substeps.model.parameter

import java.lang.Integer
import collection.JavaConversions._


object IntegerConverter extends Converter[Integer] {

  val converts: java.util.Set[Class[_]] = Set[Class[_]](classOf[Int])
  def convert(value: String) = Integer.valueOf(value)
}
