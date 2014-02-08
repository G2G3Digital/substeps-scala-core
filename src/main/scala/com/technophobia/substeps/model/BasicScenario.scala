package com.technophobia.substeps.model

import com.technophobia.substeps.model.execution.RunResult

class BasicScenario(tags: Set[Tag], steps: List[ParameterizedSubstepInvocation]) extends Scenario(tags, steps) {

  def run(): RunResult = steps.foldLeft[RunResult](RunResult.NoneRun)((b,a) => b.combine(a.run(Map())))

}
object BasicScenario {

  def apply(scenarioTitle: String, stepInvocations: List[String]) : BasicScenario = {

    new BasicScenario(List(), stepInvocations.map(ParameterizedSubstepInvocation(_)))

  }

}