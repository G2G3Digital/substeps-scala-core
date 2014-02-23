package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.execution.RunResult
import com.technophobia.substeps.domain.repositories.SubstepRepository

class BasicScenario(title: String, val steps: Seq[SubstepInvocation], tags: Set[Tag]) extends Scenario(title, tags) {


  def run(): RunResult = {

    steps.foldLeft[RunResult](RunResult.NoneRun)((b,a) => b.combine(a.run()))
  }


}
object BasicScenario {

  def apply(substepRepository: SubstepRepository, scenarioTitle: String, stepInvocations: Seq[String], tags: Set[Tag]) : BasicScenario = {

    new BasicScenario(scenarioTitle, stepInvocations.map(SubstepInvocation(substepRepository, _)), tags)

  }

}