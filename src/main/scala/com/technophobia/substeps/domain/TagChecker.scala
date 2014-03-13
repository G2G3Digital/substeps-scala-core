package com.technophobia.substeps.domain

sealed abstract class TagChecker {

  def shouldRunFor(tags: Set[Tag]) : Boolean
}
object TagChecker {

  def fromExclusions(exclusions: Set[Tag]) = new TagChecker() {

    override def shouldRunFor(tags: Set[Tag]) = tags.forall(!exclusions.contains(_))
  }

  def fromInclusions(inclusions: Set[Tag]) = new TagChecker {

    override def shouldRunFor(tags: Set[Tag]) = tags.exists(inclusions.contains(_))
  }

  val runAll = new TagChecker {

    override def shouldRunFor(tags: Set[Tag]) = true
  }
}