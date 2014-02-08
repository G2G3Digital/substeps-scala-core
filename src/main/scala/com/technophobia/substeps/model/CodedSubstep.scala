package com.technophobia.substeps.model

import scala.util.matching.Regex
import com.technophobia.substeps.model.execution.RunResult

class CodedSubstep(signature: Regex, function: Function) extends Substep(signature) {


  def run(invocation: String): RunResult =
}
