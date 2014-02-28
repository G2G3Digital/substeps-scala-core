package com.technophobia.substeps.domain.events

import java.util.Date

/**
 * @author rbarefield
 */
case class ParsingStarted(fileName: String, override val timeOccurred: Date = new Date) extends SubstepsDomainEvent