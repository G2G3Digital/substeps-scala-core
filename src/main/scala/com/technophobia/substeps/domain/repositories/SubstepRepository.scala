package com.technophobia.substeps.domain.repositories

import com.technophobia.substeps.domain.{MissingSubstep, Substep}
import scala.util.matching.Regex

class SubstepRepository {

  var substepMap: Map[Regex, Substep] = Map()

  def clear() { substepMap = Map() }

  def find(invocation: String): Substep = {

    val matchedSubsteps = (for(pair <- substepMap; if pair._1.unapplySeq(invocation).isDefined) yield pair._2).toList

    matchedSubsteps match {

      case step :: Nil => step
      case step :: others => throw new RuntimeException("TODO deal with case where more than one substep matched")
      case Nil => new MissingSubstep(invocation)
    }

  }

  def add(substep: Substep) { substepMap += (substep.regex -> substep) }
}
