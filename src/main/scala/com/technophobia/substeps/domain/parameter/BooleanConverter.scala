package com.technophobia.substeps.domain.parameter

import java.lang.Boolean
import collection.JavaConversions._
import com.technophobia.substeps.model.parameter.Converter

object BooleanConverter extends Converter[Boolean] {

  val converts: java.util.Set[Class[_]] = Set[Class[_]](classOf[Boolean])
  def convert(value: String): Boolean = Boolean.valueOf(value)
}
