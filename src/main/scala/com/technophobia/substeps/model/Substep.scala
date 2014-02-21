package com.technophobia.substeps.model

import scala.util.matching.Regex
import com.technophobia.substeps.repositories.SubstepRepository


/**
 * @author rbarefield
 */
abstract class Substep(val regex: Regex) {

  assert(regex != null)

  def createInvocation(substepRepository: SubstepRepository, invocation: String) : SubstepInvocation
}
