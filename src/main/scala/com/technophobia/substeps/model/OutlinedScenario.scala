package com.technophobia.substeps.model

import com.technophobia.substeps.model.execution.RunResult

class OutlinedScenario(outlineTitle: String, derivedScenarios: Ordered[BasicScenario], tags: Set[Tag]) extends Scenario(tags) {

  def run(): RunResult = null
}
object OutlinedScenario {


  def apply(outlineTitle: String, outline: Seq[String], examples: List[Map[String, String]], tags: Set[Tag]): OutlinedScenario = {

    def applyExampleToSubstepInvocation()


    for(example <- examples; outlineStep <- outline; )

    new OutlinedScenario(outlineTitle, null, tags)
  }
}