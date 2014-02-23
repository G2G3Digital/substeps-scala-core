package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.execution.RunResult

/**
 * @author rbarefield
 */
class MissingSubstepInvocation(invocationString: String) extends SubstepInvocation {

  def run(): RunResult = RunResult.Failed("Missing Substep: " + invocationString)
}
