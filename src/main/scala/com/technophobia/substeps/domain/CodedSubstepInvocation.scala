package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.execution.RunResult
import java.lang.reflect.InvocationTargetException

/**
 * @author rbarefield
 */
case class CodedSubstepInvocation(f: () => Any) extends SubstepInvocation {

  def run(): RunResult = {

    try {

      f()
      RunResult.Passed

    } catch {

      case a: InvocationTargetException => {

        a.getCause match {

          case nested: AssertionError => RunResult.Failed(nested.getMessage)
          case _ => RunResult.Failed(a.getCause.getMessage)
        }


      }
    }

  }
}
