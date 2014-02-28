package com.technophobia.substeps.domain.events

import java.util.Date

/**
 * @author rbarefield
 */
case class ExecutionStarted(val nodeType: AnyRef, override val timeOccurred: Date = new Date) extends SubstepsDomainEvent {

}