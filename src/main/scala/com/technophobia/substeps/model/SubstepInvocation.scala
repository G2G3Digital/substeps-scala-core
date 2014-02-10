package com.technophobia.substeps.model

import java.util.regex.Pattern
import com.technophobia.substeps.repositories.SubstepRepository
import com.technophobia.substeps.model.execution.RunResult

abstract class SubstepInvocation {

  def run(): RunResult
}
object SubstepInvocation {

  def apply(invocation: String) : SubstepInvocation = {

    SubstepRepository.find(invocation).createInvocation(invocation)
  }

}