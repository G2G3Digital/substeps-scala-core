package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.repositories.SubstepRepository

/**
 * @author rbarefield
 */
class MissingSubstep(invocationString: String) extends Substep("".r) {

  def createInvocation(substepRepository: SubstepRepository, invocation: String) = new MissingSubstepInvocation(invocationString)
}
