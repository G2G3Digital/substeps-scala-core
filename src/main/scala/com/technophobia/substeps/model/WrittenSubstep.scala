package com.technophobia.substeps.model

import java.util.regex.Pattern
import com.technophobia.substeps.model.execution.RunResult
import scala.util.matching.Regex
import com.technophobia.substeps.repositories.SubstepRepository

class WrittenSubstep(signature: Regex, substepInvocations: Ordered[ParameterizedSubstepInvocation]) extends Substep(signature) {

  def run(invocation: String): RunResult = {

    val parameterMap = deriveParameters(invocation)

    SubstepRepository.find(invocation).run(invocation)

  }

  private def deriveParameters(invocation: String) : Map[String, String] = {

    Map()
  }
}
