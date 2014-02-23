package com.technophobia.substeps.domain

import scala.util.matching.Regex
import scala.Predef._
import com.technophobia.substeps.domain.repositories.SubstepRepository


case class WrittenSubstep(signature: String, invocationLines: String*) extends Substep(WrittenSubstep.asRegEx(signature)) {

  val parameterNames: List[String]= (for(paramWithArrows <- "<[^>]+>".r.findAllIn(signature)) yield paramWithArrows.substring(0, paramWithArrows.length)).toList

  def createInvocation(substepRepository: SubstepRepository, invocation: String): WrittenSubstepInvocation = {

    val parameterToValues = createParameterToValueMap(invocation)
    val rewrittenInvocationLines = invocationLines.map(rewriteWithParameters(_, parameterToValues))
    val substepInvocations = rewrittenInvocationLines.map(SubstepInvocation(substepRepository, _))

    WrittenSubstepInvocation(this, invocation, substepInvocations)
  }

  private def createParameterToValueMap(invocation: String) : Map[String, String] = {

    this.regex.findAllIn(invocation).matchData.toList(0).subgroups
    val values : List[String] = regex.findAllIn(invocation).matchData.next().subgroups

    Map(parameterNames zip values  : _*)

  }

  private def rewriteWithParameters(invocation: String, parametersToValues: Map[String, String]): String = {

    parametersToValues.foldLeft[String](invocation)((b, a) => b.replaceAllLiterally(a._1, a._2))
  }

}
object WrittenSubstep {

  def asRegEx(signature:String):Regex = ("^" + signature.replaceAll("<[^>]+>", "(.*)") + "$").r
}