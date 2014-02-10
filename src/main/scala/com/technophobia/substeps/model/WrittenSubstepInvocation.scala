package com.technophobia.substeps.model

import com.technophobia.substeps.model.execution.RunResult

/**
 * @author rbarefield
 */
case class WrittenSubstepInvocation(builtFrom: Substep, invocationLine: String, substepInvocations: Seq[SubstepInvocation]) extends SubstepInvocation {

  def run() : RunResult = {

    substepInvocations.foldLeft[RunResult](RunResult.NoneRun)((b, a) => b.combine(a.run()))
  }
}
