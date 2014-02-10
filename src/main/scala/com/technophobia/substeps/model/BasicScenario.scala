package com.technophobia.substeps.model

import com.technophobia.substeps.model.execution.RunResult

class BasicScenario(tags: Set[Tag], steps: List[SubstepInvocation]) extends Scenario(tags) {

  def run(): RunResult = steps.foldLeft[RunResult](RunResult.NoneRun)((b,a) => b.combine(a.run()))

}
object BasicScenario {

  def apply(scenarioTitle: String, stepInvocations: List[String]) : BasicScenario = {

    new BasicScenario(Set(), stepInvocations.map(SubstepInvocation(_)))

  }

}