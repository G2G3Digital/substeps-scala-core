package com.technophobia.substeps.services

import com.technophobia.substeps.domain.events.{ExecutionCompleted, ExecutionStarted, SubstepsDomainEvent, DomainEventSubscriber}
import java.lang.reflect.Method
import org.reflections.ReflectionUtils
import com.technophobia.substeps.runner.setupteardown.Annotations
import java.lang.annotation.Annotation
import collection.JavaConversions._
import com.technophobia.substeps.domain.{BasicScenario, Feature}
import scala.collection.mutable.ListBuffer

/**
 * @author rbarefield
 */
class SetupAndTeardownSubscriber extends DomainEventSubscriber {

  var initializationClasses: List[Class[_]] = Nil

  val beforeAllFeatures = collection.mutable.ListBuffer[() => Any]()
  val beforeEveryFeature = collection.mutable.ListBuffer[() => Any]()
  val beforeEveryScenario = collection.mutable.ListBuffer[() => Any]()
  val afterEveryScenario = collection.mutable.ListBuffer[() => Any]()
  val afterEveryFeature = collection.mutable.ListBuffer[() => Any]()
  val afterAllFeatures = collection.mutable.ListBuffer[() => Any]()

  def featuresStarting() = beforeAllFeatures.foreach(_())
  def featuresComplete() = afterAllFeatures.foreach(_())

  def addInitializationClass(initializationClass: Class[_]) {

    if(initializationClasses.contains(initializationClass)) return

    val instance = initializationClass.newInstance()

    val annotationToFunctionHolders = List[(Class[_ <: Annotation], ListBuffer[() => Any])]((classOf[Annotations.BeforeAllFeatures], beforeAllFeatures),
      (classOf[Annotations.BeforeEveryFeature], beforeEveryFeature),
      (classOf[Annotations.BeforeEveryScenario], beforeEveryScenario),
      (classOf[Annotations.AfterEveryFeature], afterEveryFeature),
      (classOf[Annotations.AfterEveryScenario], afterEveryScenario),
      (classOf[Annotations.AfterAllFeatures], afterAllFeatures))

    for((annotation, functionHolder) <- annotationToFunctionHolders;method <- methodsForAnnotation(initializationClass, annotation)){

      functionHolder.+=(() => method.invoke(instance))
    }

    initializationClasses ::= initializationClass
  }

  private def methodsForAnnotation(initializationClass: Class[_], annotation: Class[_ <: Annotation]): Set[Method] = {

    ReflectionUtils.getAllMethods(initializationClass, ReflectionUtils.withAnnotation(annotation)).toSet
  }


  def handle(event: SubstepsDomainEvent) = {


    event match {

      case ExecutionStarted(Feature(_, _, _), _) => beforeEveryFeature.foreach(_())
      case ExecutionStarted(BasicScenario(_, _, _, _), _) => beforeEveryScenario.foreach(_())

      case ExecutionCompleted(BasicScenario(_, _, _, _), _, _) => afterEveryScenario.foreach(_())
      case ExecutionCompleted(Feature(_, _, _), _, _) => afterEveryFeature.foreach(_())


      case _ => {}
    }

  }





}
