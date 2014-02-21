package com.technophobia.substeps.model

import com.technophobia.substeps.model.execution.RunResult
import com.technophobia.substeps.repositories.SubstepRepository
import com.technophobia.substeps.SubstepsLoggers

class BasicScenario(title: String, val steps: Seq[SubstepInvocation], tags: Set[Tag]) extends Scenario(title, tags) {


  implicit class andString(before: String) {

    def apply[T](result: => T)(after: String) = {

      SubstepsLoggers.compositeLogger.info(before)
      result

    }
  }


  def run(): RunResult = ("Running basic scenario: " + title){

                            steps.foldLeft[RunResult](RunResult.NoneRun)((b,a) => b.combine(a.run()))

                         }("Basic Scenario complete")

}
object BasicScenario {

  def apply(substepRepository: SubstepRepository, scenarioTitle: String, stepInvocations: Seq[String], tags: Set[Tag]) : BasicScenario = {

    new BasicScenario(scenarioTitle, stepInvocations.map(SubstepInvocation(substepRepository, _)), tags)

  }

}