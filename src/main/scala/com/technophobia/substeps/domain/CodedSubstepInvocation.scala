package com.technophobia.substeps.domain

import com.technophobia.substeps.domain.execution.RunResult
import java.lang.reflect.InvocationTargetException
import com.technophobia.substeps.domain.events.{ExecutionCompleted, ExecutionStarted, DomainEventPublisher}

/**
 * @author rbarefield
 */
case class CodedSubstepInvocation(invocationLine: String, f: () => Any) extends SubstepInvocation {

  def run(): RunResult = {

    DomainEventPublisher.instance().publish(ExecutionStarted(this))

    val result = try {

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

    DomainEventPublisher.instance().publish(ExecutionCompleted(this, result))

    result
  }
}
