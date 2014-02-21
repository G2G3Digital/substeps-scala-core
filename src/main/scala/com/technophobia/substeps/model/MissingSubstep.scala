package com.technophobia.substeps.model

import com.technophobia.substeps.repositories.SubstepRepository

/**
 * @author rbarefield
 */
class MissingSubstep(invocationString: String) extends Substep("".r) {

  def createInvocation(substepRepository: SubstepRepository, invocation: String) = new MissingSubstepInvocation(invocationString)
}
