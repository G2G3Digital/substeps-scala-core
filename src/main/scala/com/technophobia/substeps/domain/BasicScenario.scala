package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.execution.RunResult
import com.technophobia.substeps.domain.repositories.SubstepRepository
import com.technophobia.substeps.domain.events.{ExecutionCompleted, ExecutionStarted, DomainEventPublisher}
import java.util.Date

case class BasicScenario(override val title: String, val background: Option[Background], val steps: Seq[SubstepInvocation],override val  tags: Set[Tag]) extends Scenario(title, tags) {


  def run(): RunResult = {

    DomainEventPublisher.instance().publish(ExecutionStarted(this))

    val backgroundResult = background.map(_.run()).getOrElse(RunResult.Passed)

    val result = if(backgroundResult == RunResult.Passed) {

      steps.foldLeft[RunResult](RunResult.NoneRun)((b,a) => b.combine(a.run()))

    } else {

      backgroundResult
    }

    DomainEventPublisher.instance().publish(ExecutionCompleted(this, result))

    result
  }


}
object BasicScenario {

  def apply(substepRepository: SubstepRepository, scenarioTitle: String, stepInvocations: Seq[String], tags: Set[Tag]) : BasicScenario = {

    new BasicScenario(scenarioTitle, None, stepInvocations.map(SubstepInvocation(substepRepository, _)), tags)
  }

  def apply(substepRepository: SubstepRepository, scenarioTitle: String, background: Background, stepInvocations: Seq[String], tags: Set[Tag]) : BasicScenario = {

    new BasicScenario(scenarioTitle, Some(background), stepInvocations.map(SubstepInvocation(substepRepository, _)), tags)
  }
}