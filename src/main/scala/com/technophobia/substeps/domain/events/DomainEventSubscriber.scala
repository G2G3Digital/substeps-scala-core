package com.technophobia.substeps.domain.events

/**
 * @author rbarefield
 */
trait DomainEventSubscriber {

  def handle(event: SubstepsDomainEvent)
}
