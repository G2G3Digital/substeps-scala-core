package com.technophobia.substeps.services

import java.io.{FileReader, File}
import com.technophobia.substeps.parsing.{FeatureFileParser, ParseFailureException, SubstepsFileParser}
import com.technophobia.substeps.domain.repositories.SubstepRepository
import com.technophobia.substeps.domain.{CodedSubstep, Feature}
import java.lang.reflect.Method
import org.reflections.ReflectionUtils
import com.technophobia.substeps.model.SubSteps
import SubSteps.Step
import com.technophobia.substeps.domain.execution.RunResult
import collection.JavaConversions._

class SubstepsSession {

  val substepRepository = new SubstepRepository
  var features = Map[String, Feature]()

  @throws[ParseFailureException]
  def addSubsteps(substepsFile: File) {

    val substeps = new SubstepsFileParser().parseOrFail(new FileReader(substepsFile))
    substeps.foreach(substepRepository.add)
  }

  @throws[ParseFailureException]
  def addFeature(featureFile: File) {

    val feature = new FeatureFileParser(substepRepository).parseOrFail(new FileReader(featureFile))
    features += (feature.name -> feature)
  }

  def addCodedSubsteps(codedSubstepClassInstance: AnyRef) {

    val codedSubsteps = for{method: Method <- ReflectionUtils.getAllMethods(codedSubstepClassInstance.getClass, ReflectionUtils.withAnnotation(classOf[Step]))
        stepAnnotation = method.getAnnotation(classOf[Step])} yield CodedSubstep(stepAnnotation.value().r, method, codedSubstepClassInstance)

    codedSubsteps.foreach(substepRepository.add)
  }

  def run(tags: java.util.List[String]) = {

    features.values.foldLeft[RunResult](RunResult.NoneRun)((b, a) => b.combine(a.run()))
  }
}
