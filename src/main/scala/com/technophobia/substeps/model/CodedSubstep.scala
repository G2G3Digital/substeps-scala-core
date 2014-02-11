package com.technophobia.substeps.model

import scala.util.matching.Regex
import com.technophobia.substeps.model.execution.RunResult
import java.lang.reflect.Method
import com.technophobia.substeps.model.parameter.Converter

case class CodedSubstep(signature: Regex, method: Method, instance: AnyRef) extends Substep(signature) {

  val argumentTypes: Seq[Class[_]] = method.getParameterTypes.toList


  def createInvocation(invocation: String) = {

    def extractInputs(invocation: String): Seq[String] = {

      regex.findAllIn(invocation).matchData.toList(0).subgroups
    }

    def coerceInputs(inputStrings: Seq[String]): Seq[AnyRef] = {

      val zipped : Seq[(String, Class[_])] = (inputStrings zip argumentTypes)
      zipped.map(p => Converter.convert(p._1, p._2))
    }

    val inputStrings = extractInputs(invocation)
    val inputsCoerced = coerceInputs(inputStrings)
    createInvocation(inputsCoerced)
  }

  private def createInvocation(inputs: Seq[AnyRef]) : CodedSubstepInvocation = {

    CodedSubstepInvocation(() => method.invoke(instance, inputs:_*))
  }
}
