package com.technophobia.substeps.model

import com.technophobia.substeps.model.execution.RunResult

class BasicScenario(title: String, steps: List[SubstepInvocation], tags: Set[Tag]) extends Scenario(tags) {

  def run(): RunResult = steps.foldLeft[RunResult](RunResult.NoneRun)((b,a) => b.combine(a.run()))

}
object BasicScenario {

  def apply(scenarioTitle: String, stepInvocations: List[String], tags: Set[Tag]) : BasicScenario = {

    new BasicScenario(scenarioTitle, stepInvocations.map(SubstepInvocation(_)), tags)

  }

}