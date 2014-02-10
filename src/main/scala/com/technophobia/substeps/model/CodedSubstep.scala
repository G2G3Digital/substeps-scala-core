package com.technophobia.substeps.model

import scala.util.matching.Regex
import com.technophobia.substeps.model.execution.RunResult
import java.lang.reflect.Method

case class CodedSubstep(signature: Regex, method: Method, instance: AnyRef) extends Substep(signature) {

  val argumentTypes = method.getParameterTypes.toList

  def extractInputs(invocation: String): Seq[Any] = {

    //TODO
  }

  def createInvocation(invocation: String) = createInvocation(extractInputs(invocation))

  private def createInvocation(inputs: Any*) : CodedSubstepInvocation = {

    CodedSubstepInvocation(() => method.invoke(instance, inputs))
  }
}
