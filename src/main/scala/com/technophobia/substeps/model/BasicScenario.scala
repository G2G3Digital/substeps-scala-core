package com.technophobia.substeps.model

import com.technophobia.substeps.model.execution.RunResult
import com.technophobia.substeps.repositories.SubstepRepository
import com.technophobia.substeps.SubstepsLoggers

class BasicScenario(title: String, val steps: Seq[SubstepInvocation], tags: Set[Tag]) extends Scenario(title, tags) {

  def run(): RunResult = {

    SubstepsLoggers.compositeLogger.info("Running basic scenario: " + title)
    val result = steps.foldLeft[RunResult](RunResult.NoneRun)((b,a) => b.combine(a.run()))
    SubstepsLoggers.compositeLogger.info("Basic scenario complete")
    result
  }

}
object BasicScenario {

  def apply(substepRepository: SubstepRepository, scenarioTitle: String, stepInvocations: Seq[String], tags: Set[Tag]) : BasicScenario = {

    new BasicScenario(scenarioTitle, stepInvocations.map(SubstepInvocation(substepRepository, _)), tags)

  }

}