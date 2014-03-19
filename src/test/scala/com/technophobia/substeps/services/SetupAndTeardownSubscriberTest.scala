package com.technophobia.substeps.services

import com.technophobia.substeps.runner.setupteardown.Annotations
import org.junit.{Assert, Test}
import com.technophobia.substeps.domain.events.{ExecutionCompleted, SubstepsDomainEvent, ExecutionStarted}
import com.technophobia.substeps.domain.{Background, Tag, BasicScenario, Feature}

/**
 * @author rbarefield
 */
class SetupAndTeardownSubscriberTest {

  @Test
  def testDomainEvents() {

    assertEventCausesState(ExecutionStarted(Feature(null, null, List(), Set())), false, true, false, false, false, false)
    assertEventCausesState(ExecutionStarted(BasicScenario(null, Seq(), Set())), false, false, true, false, false, false)
    assertEventCausesState(ExecutionCompleted(Feature(null, null, List(), Set()), null, null), false, false, false, false, true, false)
    assertEventCausesState(ExecutionCompleted(BasicScenario(null, Seq(), Set()), null), false, false, false, false, false, true)
  }

  private def assertEventCausesState(event: SubstepsDomainEvent, expectBeforeAllFeatures: Boolean, 
                                     expectBeforeEveryFeature: Boolean, expectBeforeEveryScenario: Boolean, 
                                     expectAfterAllFeatures: Boolean, expectAfterEveryFeature: Boolean, 
                                     expectAfterEveryScenario: Boolean) {

    SetupAndTeardownSubscriberTest.testInitializationClasses = Nil
    val setupAndTeardownSubscriber = new SetupAndTeardownSubscriber
    setupAndTeardownSubscriber.addInitializationClass(classOf[TestInitializationClass])

    setupAndTeardownSubscriber.handle(event)

    Assert.assertEquals(1, SetupAndTeardownSubscriberTest.testInitializationClasses.size)

    val initializationClass = SetupAndTeardownSubscriberTest.testInitializationClasses.head

    initializationClass.assert(expectBeforeAllFeatures, expectBeforeEveryFeature, expectBeforeEveryScenario,
      expectAfterAllFeatures, expectAfterEveryFeature, expectAfterEveryScenario)
    
  }

}

object SetupAndTeardownSubscriberTest {

  var testInitializationClasses: List[TestInitializationClass] = Nil

}

class TestInitializationClass {

  SetupAndTeardownSubscriberTest.testInitializationClasses ::= this

  var beforeAllFeaturesCalled = false

  @Annotations.BeforeAllFeatures
  def beforeAllFeatures() {
    beforeAllFeaturesCalled = true
  }

  var beforeEveryFeaturesCalled = false

  @Annotations.BeforeEveryFeature
  def beforeEveryFeature() {
    beforeEveryFeaturesCalled = true
  }

  var beforeEveryScenarioCalled = false

  @Annotations.BeforeEveryScenario
  def beforeEveryScenario() {
    beforeEveryScenarioCalled = true
  }

  var afterAllFeaturesCalled = false

  @Annotations.AfterAllFeatures
  def afterAllFeatures() {
    afterAllFeaturesCalled = true
  }

  var afterEveryFeaturesCalled = false

  @Annotations.AfterEveryFeature
  def afterEveryFeature() {
    afterEveryFeaturesCalled = true
  }

  var afterEveryScenarioCalled = false

  @Annotations.AfterEveryScenario
  def afterEveryScenario() {
    afterEveryScenarioCalled = true
  }

  def assert(expectBeforeAllFeatures: Boolean, expectBeforeEveryFeature: Boolean, expectBeforeEveryScenario: Boolean, expectAfterAllFeatures: Boolean, expectAfterEveryFeature: Boolean, expectAfterEveryScenario: Boolean) {

    Assert.assertEquals(expectBeforeAllFeatures, beforeAllFeaturesCalled)
    Assert.assertEquals(expectBeforeEveryFeature, beforeEveryFeaturesCalled)
    Assert.assertEquals(expectBeforeEveryScenario, beforeEveryScenarioCalled)
    Assert.assertEquals(expectAfterAllFeatures, afterAllFeaturesCalled)
    Assert.assertEquals(expectAfterEveryFeature, afterEveryFeaturesCalled)
    Assert.assertEquals(expectAfterEveryScenario, afterEveryScenarioCalled)
  }

}