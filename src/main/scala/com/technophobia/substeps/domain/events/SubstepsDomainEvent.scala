package com.technophobia.substeps.domain.events

import java.util.Date

/**
 * @author rbarefield
 */
trait SubstepsDomainEvent {

  def timeOccurred(): Date

}
