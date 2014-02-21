package com.technophobia.substeps.model

import com.technophobia.substeps.model.execution.RunResult

/**
 * @author rbarefield
 */
class MissingSubstepInvocation(invocationString: String) extends SubstepInvocation {

  def run(): RunResult = RunResult.Failed("Missing Substep: " + invocationString)
}
