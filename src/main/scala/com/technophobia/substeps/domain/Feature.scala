package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.execution.RunResult
import com.technophobia.substeps.domain.events._
import com.technophobia.substeps.domain.events.ExecutionStarted
import com.technophobia.substeps.domain.events.ExecutionCompleted

case class Feature(name: String, background: Option[Background], scenarios: List[Scenario], tags: Set[Tag]) {

  def isApplicableGiven(tagChecker: TagChecker) = {

    tagChecker.shouldRunFor(tags)
  }

  def run(tagChecker: TagChecker):RunResult = {

    DomainEventPublisher.instance().publish(ExecutionStarted(this))

    val (scenariosToRun, scenariosToSkip) = scenarios.partition(_.isApplicableGiven(tagChecker))

    scenariosToSkip.foreach[Unit](f => DomainEventPublisher.instance().publish(ScenarioSkipped(f)))

    def runWithBackground(scenario: Scenario) = {

      val backgroundResult = background.map(_.run()).getOrElse(RunResult.Passed)

      if(backgroundResult.isPass) {

        scenario.run()

      } else {

        backgroundResult
      }
    }

    val result = scenariosToRun.foldLeft[RunResult](RunResult.NoneRun)((b, a) => b.combine(runWithBackground(a)))

    DomainEventPublisher.instance().publish(ExecutionCompleted(this, result))

    result
  }

}
