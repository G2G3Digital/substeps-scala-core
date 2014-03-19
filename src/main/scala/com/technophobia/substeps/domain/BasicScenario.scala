package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.execution.RunResult
import com.technophobia.substeps.domain.repositories.SubstepRepository
import com.technophobia.substeps.domain.events.{ExecutionCompleted, ExecutionStarted, DomainEventPublisher}
import java.util.Date

case class BasicScenario(override val title: String, val steps: Seq[SubstepInvocation],override val  tags: Set[Tag]) extends Scenario(title, tags) {

  assert(steps != null, "Steps must not be null for a BasicScenario")

  def run(): RunResult = {

    DomainEventPublisher.instance().publish(ExecutionStarted(this))

    val result = steps.foldLeft[RunResult](RunResult.NoneRun)((b,a) => b.combine(a.run()))

    DomainEventPublisher.instance().publish(ExecutionCompleted(this, result))

    result
  }


}
object BasicScenario {

  def apply(substepRepository: SubstepRepository, scenarioTitle: String, stepInvocations: Seq[String], tags: Set[Tag]) : BasicScenario = {

    new BasicScenario(scenarioTitle, stepInvocations.map(SubstepInvocation(substepRepository, _)), tags)
  }
}