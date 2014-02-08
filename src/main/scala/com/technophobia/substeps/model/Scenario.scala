package com.technophobia.substeps.model

import com.technophobia.substeps.model.execution.RunResult

abstract class Scenario(tags: Set[Tag], steps: List[SubstepInvocation]) {

  def run():RunResult

}
