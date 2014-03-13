package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.execution.RunResult
import com.technophobia.substeps.domain.events.{ExecutionCompleted, ExecutionStarted, DomainEventPublisher}

case class Feature(name: String, background: Option[Background], scenarios: List[Scenario], tags: Set[Tag]) {

  def isApplicableGiven(tagChecker: TagChecker) = {

    tagChecker.shouldRunFor(tags)
  }

  def run():RunResult = {


    DomainEventPublisher.instance().publish(ExecutionStarted(this))

    def runWithBackground(scenario: Scenario) = {

      val backgroundResult = background.map(_.run()).getOrElse(RunResult.Passed)

      if(backgroundResult.isPass) {

        scenario.run()

      } else {

        backgroundResult
      }
    }

    val result = scenarios.foldLeft[RunResult](RunResult.NoneRun)((b, a) => b.combine(runWithBackground(a)))


    DomainEventPublisher.instance().publish(ExecutionCompleted(this, result))

    result
  }

}
