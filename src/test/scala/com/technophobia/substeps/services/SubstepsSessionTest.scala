package com.technophobia.substeps.services

import com.technophobia.substeps.domain.events.{FeatureSkipped, SubstepsDomainEvent, DomainEventSubscriber}

import collection.JavaConversions._
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import com.technophobia.substeps.domain.Feature
import org.mockito.{Spy, Mockito, Mock}
import org.junit.{Test, Assert, Before}
import com.technophobia.substeps.domain.execution.RunResult

/**
 * @author rbarefield
 */
@RunWith(classOf[MockitoJUnitRunner])
class SubstepsSessionTest extends DomainEventSubscriber {

  @Spy
  var coreFeature: Feature = new Feature(null, null, null, Set("core"))

  @Spy
  var coreFeature2: Feature = new Feature(null, null, null, Set("core"))

  @Spy
  var coreButWip: Feature = new Feature(null, null, null, Set("core", "wip"))

  @Spy
  var notTagged: Feature = new Feature(null, null, null, Set())

  var events = List[SubstepsDomainEvent]()

  @Before
  def clearEvents() {

    events = List()
    Mockito.doReturn(RunResult.Passed).when(coreFeature).run()
    Mockito.doReturn(RunResult.Passed).when(coreFeature2).run()
    Mockito.doReturn(RunResult.Passed).when(coreButWip).run()
    Mockito.doReturn(RunResult.Passed).when(notTagged).run()

  }

  @Test
  def testRunsAll() {

    val session = new SubstepsSession(Set[DomainEventSubscriber](this))

    session.features += ("mock1" -> coreFeature)
    session.features += ("mock2" -> coreFeature2)


    session.run(None, Set())

    Mockito.verify(coreFeature, Mockito.times(1)).run()
    Mockito.verify(coreFeature2, Mockito.times(1)).run()

    Assert.assertEquals(0, events.size)
  }

  @Test
  def testRunsIncluded() {

    val session = new SubstepsSession(Set[DomainEventSubscriber](this))

    session.features += ("core" -> coreFeature)
    session.features += ("core2" -> coreFeature2)
    session.features += ("coreButWip" -> coreButWip)
    session.features += ("untagged" -> notTagged)

    session.run(Some(Set("core")), Set())

    Mockito.verify(coreFeature, Mockito.times(1)).run()
    Mockito.verify(coreFeature2, Mockito.times(1)).run()
    Mockito.verify(coreButWip, Mockito.times(1)).run()
    Mockito.verify(notTagged, Mockito.times(0)).run()

    Assert.assertEquals(1, events.size)

    val event = events(0).asInstanceOf[FeatureSkipped]
    Assert.assertEquals(notTagged, event.feature)
  }

  @Test
  def testRunsIncludedExceptThoseExcluded() {

    val session = new SubstepsSession(Set[DomainEventSubscriber](this))

    session.features += ("core" -> coreFeature)
    session.features += ("core2" -> coreFeature2)
    session.features += ("coreButWip" -> coreButWip)
    session.features += ("untagged" -> notTagged)

    session.run(Some(Set("core")), Set("wip"))

    Mockito.verify(coreFeature, Mockito.times(1)).run()
    Mockito.verify(coreFeature2, Mockito.times(1)).run()
    Mockito.verify(coreButWip, Mockito.times(0)).run()
    Mockito.verify(notTagged, Mockito.times(0)).run()

    Assert.assertEquals(2, events.size)
  }

  def handle(event: SubstepsDomainEvent): Unit = events ::= event
}
