package com.technophobia.substeps.services

import java.io.{FileReader, File}
import com.technophobia.substeps.parsing.{FeatureFileParser, ParseFailureException, SubstepsFileParser}
import com.technophobia.substeps.domain.repositories.SubstepRepository
import com.technophobia.substeps.domain.{TagChecker, Tag, CodedSubstep, Feature}
import java.lang.reflect.Method
import org.reflections.ReflectionUtils
import com.technophobia.substeps.model.SubSteps
import com.technophobia.substeps.model.SubSteps.{StepImplementations, Step}
import com.technophobia.substeps.domain.execution.RunResult
import collection.JavaConversions._
import com.technophobia.substeps.domain.events.{FeatureSkipped, DomainEventPublisher, DomainEventSubscriber}

class SubstepsSession(val subscribers: java.util.Set[DomainEventSubscriber]) {

  val substepRepository = new SubstepRepository
  var features = Map[String, Feature]()

  val setupAndTeardownSubscriber = new SetupAndTeardownSubscriber()

  @throws[ParseFailureException]
  def addSubsteps(substepsFile: File) {

    addSubscribersToDomainEventPublisher()
    val substeps = new SubstepsFileParser().parseOrFail(substepsFile.getName, new FileReader(substepsFile))
    substeps.foreach(substepRepository.add)
  }

  @throws[ParseFailureException]
  def addFeature(featureFile: File) {

    addSubscribersToDomainEventPublisher()
    val feature = new FeatureFileParser(substepRepository).parseOrFail(featureFile.getName, new FileReader(featureFile))
    features += (feature.name -> feature)
  }

  def addCodedSubsteps(codedSubstepClass: Class[_]) {

    val codedSubstepsClassInstance = codedSubstepClass.newInstance().asInstanceOf[AnyRef]

    addSubscribersToDomainEventPublisher()
    val codedSubsteps = for{method: Method <- ReflectionUtils.getAllMethods(codedSubstepClass, ReflectionUtils.withAnnotation(classOf[Step]))
        stepAnnotation = method.getAnnotation(classOf[Step])} yield CodedSubstep(stepAnnotation.value().r, method, codedSubstepsClassInstance)

    val requiredInitializationClasses = codedSubstepClass.getAnnotation(classOf[StepImplementations]).requiredInitialisationClasses()

    requiredInitializationClasses.foreach(setupAndTeardownSubscriber.addInitializationClass(_))

    codedSubsteps.foreach(substepRepository.add)
  }

  def run(): RunResult = {

    run(TagChecker.runAll)
  }

  def runWith(inclusions: Set[Tag]): RunResult = {

    run(TagChecker.fromInclusions(inclusions))
  }

  def runWithout(exclusions: Set[Tag]): RunResult = {

    run(TagChecker.fromExclusions(exclusions))
  }

  private def run(tagChecker: TagChecker): RunResult = {

    addSubscribersToDomainEventPublisher()

    val (featuresToRun, featuresToSkip) = features.values.partition(_.isApplicableGiven(tagChecker))

    featuresToSkip.foreach[Unit](f => DomainEventPublisher.instance().publish(FeatureSkipped(f)))

    setupAndTeardownSubscriber.featuresStarting()
    val result = featuresToRun.foldLeft[RunResult](RunResult.NoneRun)((b, a) => b.combine(a.run()))
    setupAndTeardownSubscriber.featuresComplete()
    result
  }

  private def addSubscribersToDomainEventPublisher() {

    val publisher = DomainEventPublisher.instance()
    publisher.reset()
    subscribers.foreach(publisher.subscribe(_))
    publisher.subscribe(setupAndTeardownSubscriber)
  }
}
