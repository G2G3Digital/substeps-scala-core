package com.technophobia.substeps

package com.technophobia.substeps

import collection.JavaConversions._
import org.reflections.{ReflectionUtils, Reflections}
import model.SubSteps.StepImplementations
import model.SubSteps.Step
import repositories.SubstepRepository
import model.CodedSubstep
import java.lang.reflect.Method

object CodedSubstepLoader {

  def loadStepImplementations(basePackages : List[String]) = {

    for{basePackage <- basePackages
        reflections = new Reflections(basePackage)
        stepImplClass <- reflections.getTypesAnnotatedWith(classOf[StepImplementations])
        method: Method <- ReflectionUtils.getAllMethods(stepImplClass, ReflectionUtils.withAnnotation(classOf[Step]));
        stepAnnotation = method.getAnnotation(classOf[Step])
    }
    yield CodedSubstep(stepAnnotation.value().r, method, stepImplClass)
  }
}