package com.technophobia.substeps.model

import java.util.regex.Pattern
import com.technophobia.substeps.model.execution.RunResult
import scala.util.matching.Regex

abstract class Substep(val signature: Regex) {

  def run(invocation: String): RunResult
}
