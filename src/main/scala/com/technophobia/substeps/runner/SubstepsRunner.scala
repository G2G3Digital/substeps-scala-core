package com.technophobia.substeps.runner

import java.io.Reader
import com.technophobia.substeps.{SubstepsFileParser, FeatureFileParser}
import org.reflections.Reflections
import org.reflections.util.ConfigurationBuilder
import com.technophobia.substeps.model.SubSteps.Step

object SubstepsRunner {


  def run(tags: List[String], featureFiles: List[Reader], substepFiles: List[Reader], basePackages: List[String]) {

    val featureFileParser = new FeatureFileParser
    val substepsFileParser = new SubstepsFileParser

    val features = featureFiles.map(featureFileParser.parseOrFail(_))
    val substeps = substepFiles.map(substepsFileParser.parseOrFail(_))
//    StepImplementationLoader.loadStepImplementations(basePackages)
  }

}
