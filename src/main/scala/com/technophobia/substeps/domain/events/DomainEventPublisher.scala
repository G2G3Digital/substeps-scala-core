package com.technophobia.substeps.domain.events

/**
 * @author rbarefield
 */
class DomainEventPublisher {

  def publish(event: SubstepsDomainEvent) {

    DomainEventPublisher.subscribers.get().foreach(_.handle(event))
  }

  def subscribe(subscriber: DomainEventSubscriber) {

    val subscribers = DomainEventPublisher.subscribers.get()
    DomainEventPublisher.subscribers.set(subscriber :: subscribers)
  }

  def reset() {

    DomainEventPublisher.subscribers.set(Nil)
  }

}
object DomainEventPublisher {

  val subscribers = new ThreadLocal[List[DomainEventSubscriber]] {

    override def initialValue = Nil
  }

  def instance() = new DomainEventPublisher
}
