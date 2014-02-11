package com.technophobia.substeps.model.parameter

trait Converter {

  def converts: Class[_]
  def convert(value: String): AnyRef
}
object Converter {

  val converters = List(BooleanConverter, DoubleConverter, IntegerConverter, LongConverter, StringConverter)
  val converterMap: Map[Class[_], Converter] = Map(converters.map(a => (a.converts, a)):_*)

  def convert(value: String, desiredType: Class[_]) : AnyRef = {

    return converterMap(desiredType).convert(value)
  }
}