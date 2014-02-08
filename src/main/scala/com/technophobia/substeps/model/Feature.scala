package com.technophobia.substeps.model

import com.technophobia.substeps.model.execution.RunResult

class Feature(val scenarios: List[Scenario], val tags: Set[Tag]) {

  def run():RunResult = scenarios.foldLeft[RunResult](RunResult.NoneRun)((b,a) => b.combine(a.run()))
}
