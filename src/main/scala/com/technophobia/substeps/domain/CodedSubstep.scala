package com.technophobia.substeps.domain

import scala.util.matching.Regex
import java.lang.reflect.Method
import com.technophobia.substeps.domain.parameter.ConverterFactory
import com.technophobia.substeps.domain.repositories.SubstepRepository

case class CodedSubstep(signature: Regex, method: Method, instance: AnyRef) extends Substep(signature) {

  val argumentTypes: Seq[Class[_]] = method.getParameterTypes.toList


  def createInvocation(substepRepository: SubstepRepository, invocation: String) = {

    def extractInputs(invocation: String): Seq[String] = {

      regex.findAllIn(invocation).matchData.toList(0).subgroups
    }

    def coerceInputs(inputStrings: Seq[String]): Seq[AnyRef] = {

      val zipped : Seq[(String, Class[_])] = inputStrings zip argumentTypes
      zipped.map(p => ConverterFactory.convert(p._1, p._2))
    }

    val inputStrings = extractInputs(invocation)
    val inputsCoerced = coerceInputs(inputStrings)
    createInvocation(invocation, inputsCoerced)
  }

  private def createInvocation(invocationLine: String, inputs: Seq[AnyRef]) : CodedSubstepInvocation = {

    CodedSubstepInvocation(invocationLine, () => method.invoke(instance, inputs:_*))
  }
}
