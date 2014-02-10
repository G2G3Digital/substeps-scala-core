package com.technophobia.substeps.model

import com.technophobia.substeps.model.execution.RunResult

/**
 * @author rbarefield
 */
case class CodedSubstepInvocation(f: () => Any) extends SubstepInvocation {

  def run(): RunResult = {

    try {

      f()

    } catch {

      case a: AssertionError => RunResult.Failed
      case b: Exception => RunResult.Failed
    }

    RunResult.Passed
  }
}
