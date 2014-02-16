package com.technophobia.substeps.model.parameter

import collection.JavaConversions._

object ConverterFactory {

  val converters = List(BooleanConverter, DoubleConverter, IntegerConverter, LongConverter, StringConverter)

  val converterPairs: Seq[(Class[_], Converter[_])] = for(converter <- converters; clazz <- converter.converts) yield (clazz, converter)

  val converterMap: Map[Class[_], Converter[_]] = Map(converterPairs:_*)

  def convert(value: String, desiredType: Class[_]) : AnyRef = {

    converterMap(desiredType).convert(value).asInstanceOf[AnyRef]
  }
}