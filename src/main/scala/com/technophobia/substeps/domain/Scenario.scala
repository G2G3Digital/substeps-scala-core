package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.execution.RunResult

abstract class Scenario(val title: String, val tags: Set[Tag]) {

  def isApplicableGiven(checker: TagChecker) = checker.shouldRunFor(tags)

  def run():RunResult

}
