package com.technophobia.substeps.model

import java.util.regex.Pattern
import com.technophobia.substeps.model.execution.RunResult
import scala.util.matching.Regex
import com.technophobia.substeps.repositories.SubstepRepository
import scala.Predef._
import com.technophobia.substeps.model.WrittenSubstepInvocation


case class WrittenSubstep(signature: String, invocationLines: String*) extends Substep(WrittenSubstep.asRegEx(signature)) {

  val parameterNames: List[String]= (for(paramWithArrows <- "<[^>]+>".r.findAllIn(signature)) yield paramWithArrows.substring(0, paramWithArrows.length)).toList


  def createInvocation(invocation: String): WrittenSubstepInvocation = {

    val parameterToValues = createParameterToValueMap(invocation)
    val rewrittenInvocationLines = invocationLines.map(rewriteWithParameters(_, parameterToValues))
    val substepInvocations = rewrittenInvocationLines.map(SubstepInvocation(_))

    WrittenSubstepInvocation(this, invocation, substepInvocations)
  }

  private def createParameterToValueMap(invocation: String) : Map[String, String] = {

    this.regex.findAllIn(invocation).matchData.toList(0).subgroups
    val values : List[String] = regex.findAllIn(invocation).matchData.next().subgroups

    Map((parameterNames zip values)  : _*)

  }

  private def rewriteWithParameters(invocation: String, parametersToValues: Map[String, String]): String = {

    var withReplacements = invocation
    for((parameter, value) <- parametersToValues) {withReplacements = withReplacements.replaceAllLiterally(parameter, value)}
    withReplacements
  }

}
object WrittenSubstep {

  def asRegEx(signature:String):Regex = ("^" + signature.replaceAll("<[^>]+>", "(.*)") + "$").r
}