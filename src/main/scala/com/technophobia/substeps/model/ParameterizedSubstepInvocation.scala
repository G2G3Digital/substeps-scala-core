package com.technophobia.substeps.model

import java.util.regex.Pattern
import com.technophobia.substeps.repositories.SubstepRepository

class ParameterizedSubstepInvocation(invocation: String) {

  def run(parameterMap: Map[String, String]) = {

    SubstepRepository.find(invocation).run(invocation)
  }
}
object ParameterizedSubstepInvocation {

  def apply(invocation: String): ParameterizedSubstepInvocation = {

    new ParameterizedSubstepInvocation(invocation)
  }
}