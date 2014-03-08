package com.technophobia.substeps.domain.execution

sealed abstract class RunResult(val text: String) {

  def combine(other: RunResult): RunResult

  def isPass = false

}
object RunResult {

  object NoneRun extends RunResult("NothingRun") {

    def combine(other: RunResult) = other
  }

  object Passed extends RunResult("Passed") {

    def combine(other: RunResult) = other match {

      case Passed => Passed
      case NoneRun => Passed
      case x => x
    }

    override def isPass = true
  }

  case class Failed(reasons: List[String]) extends RunResult("Failed") {

    def combine(other: RunResult) = other match {

      case Failed(otherReason) => Failed(otherReason ::: reasons)
      case _ => this
    }
  }
  object Failed {

    def apply(reason: String): Failed = Failed(List(reason))
  }

}

