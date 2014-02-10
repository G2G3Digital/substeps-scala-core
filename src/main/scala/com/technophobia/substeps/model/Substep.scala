package com.technophobia.substeps.model

import scala.util.matching.Regex


/**
 * @author rbarefield
 */
abstract class Substep(val regex: Regex) {

  assert(regex != null)

  def createInvocation(invocation: String) : SubstepInvocation
}
