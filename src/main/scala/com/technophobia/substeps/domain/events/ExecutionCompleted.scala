package com.technophobia.substeps.domain.events

import java.util.Date
import com.technophobia.substeps.domain.execution.RunResult

/**
 * @author rbarefield
 */
case class ExecutionCompleted(val nodeType: AnyRef, val result: RunResult, override val timeOccurred: Date = new Date) extends SubstepsDomainEvent
