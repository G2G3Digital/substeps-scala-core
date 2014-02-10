package com.technophobia.substeps.model

import com.technophobia.substeps.model.execution.RunResult

class OutlinedScenario(tags: Set[Tag], derivedScenarios: Ordered[BasicScenario]) extends Scenario(tags) {

  def run(): RunResult = null
}
