package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.repositories.SubstepRepository
import com.technophobia.substeps.domain.execution.RunResult
import com.technophobia.substeps.domain.events.{ExecutionCompleted, ExecutionStarted, DomainEventPublisher}

/**
 * @author rbarefield
 */
case class Background(val title: String, steps: List[SubstepInvocation]) {

  def run() : RunResult = {

    DomainEventPublisher.instance().publish(ExecutionStarted(this))

    val result = steps.foldLeft[RunResult](RunResult.NoneRun)((b,a) => b.combine(a.run()))

    DomainEventPublisher.instance().publish(ExecutionCompleted(this, result))

    result
  }
}
object Background {

  def apply(substepRepository: SubstepRepository, title: String, steps: List[String]) : Background = {

    new Background(title, steps.map(SubstepInvocation(substepRepository, _)))
  }
}
