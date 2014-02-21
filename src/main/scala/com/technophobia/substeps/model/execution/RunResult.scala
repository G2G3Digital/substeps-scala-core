package com.technophobia.substeps.model.execution

sealed abstract class RunResult {

  def combine(other: RunResult): RunResult

}
object RunResult {

  object NoneRun extends RunResult {

    def combine(other: RunResult) = other
  }

  object Passed extends RunResult {

    def combine(other: RunResult) = other match {

      case Passed => Passed
      case NoneRun => Passed
      case x => x
    }

  }

  case class Failed(reasons: List[String]) extends RunResult {

    def combine(other: RunResult) = other match {

      case Failed(otherReason) => Failed(otherReason ::: reasons)
      case _ => this
    }
  }
  object Failed {

    def apply(reason: String): Failed = Failed(List(reason))
  }

}

