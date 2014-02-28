package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.execution.RunResult
import com.technophobia.substeps.domain.events.{ExecutionCompleted, ExecutionStarted, DomainEventPublisher}

case class Feature(val name: String, val scenarios: List[Scenario], val tags: Set[Tag]) {

  def run():RunResult = {

    DomainEventPublisher.instance().publish(ExecutionStarted(this))

    val result = scenarios.foldLeft[RunResult](RunResult.NoneRun)((b,a) => b.combine(a.run()))

    DomainEventPublisher.instance().publish(ExecutionCompleted(this, result))

    result
  }
}
