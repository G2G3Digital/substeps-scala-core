package com.technophobia.substeps.domain

import java.util.regex.Pattern
import com.technophobia.substeps.domain.repositories.SubstepRepository
import com.technophobia.substeps.domain.execution.RunResult

abstract class SubstepInvocation {

  def run(): RunResult
}
object SubstepInvocation {

  def apply(substepRepository: SubstepRepository, invocation: String) : SubstepInvocation = {

    substepRepository.find(invocation).createInvocation(substepRepository, invocation)
  }

}