package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.execution.RunResult

/**
 * @author rbarefield
 */
case class WrittenSubstepInvocation(builtFrom: Substep, val invocationLine: String, substepInvocations: Seq[SubstepInvocation]) extends SubstepInvocation {

  def run() : RunResult = {

    substepInvocations.foldLeft[RunResult](RunResult.NoneRun)((b, a) => b.combine(a.run()))
  }
}
