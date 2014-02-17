package com.technophobia.substeps.model

class Tag private (name: String)
object Tag {

  val tags = Map[String, Tag]().withDefault(new Tag(_))

  def apply(tagName: String) = tags(tagName)
}