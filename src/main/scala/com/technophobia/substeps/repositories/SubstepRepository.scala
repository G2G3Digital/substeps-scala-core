package com.technophobia.substeps.repositories

import com.technophobia.substeps.model.Substep
import scala.util.matching.Regex

object SubstepRepository {

  var substepMap: Map[Regex, Substep] = Map()

  def clear() { substepMap = Map() }

  def find(invocation: String): Substep = {

    val matchedSubsteps = for(pair <- substepMap; if pair._1.unapplySeq(invocation).isDefined) yield pair._2
    matchedSubsteps.head
  }

  def add(substep: Substep) { substepMap += (substep.regex -> substep) }
}
