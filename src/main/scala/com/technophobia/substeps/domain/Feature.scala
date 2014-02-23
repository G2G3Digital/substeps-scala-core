package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.execution.RunResult

case class Feature(val name: String, val scenarios: List[Scenario], val tags: Set[Tag]) {

  def run():RunResult = scenarios.foldLeft[RunResult](RunResult.NoneRun)((b,a) => b.combine(a.run()))
}
