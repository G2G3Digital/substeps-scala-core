package com.technophobia.substeps.model.parameter

import java.lang.Boolean
import collection.JavaConversions._

object BooleanConverter extends Converter[Boolean] {

  val converts: java.util.Set[Class[_]] = Set[Class[_]](classOf[Boolean])
  def convert(value: String): Boolean = Boolean.valueOf(value)
}
