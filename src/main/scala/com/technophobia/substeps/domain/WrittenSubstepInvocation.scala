package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.execution.RunResult
import com.technophobia.substeps.domain.events.{ExecutionCompleted, ExecutionStarted, DomainEventPublisher}

/**
 * @author rbarefield
 */
case class WrittenSubstepInvocation(builtFrom: Substep, val invocationLine: String, substepInvocations: Seq[SubstepInvocation]) extends SubstepInvocation {

  def run() : RunResult = {

    DomainEventPublisher.instance().publish(ExecutionStarted(this))

    val result = substepInvocations.foldLeft[RunResult](RunResult.NoneRun)((b, a) => b.combine(a.run()))

    DomainEventPublisher.instance().publish(ExecutionCompleted(this, result))

    result
  }
}
