package com.technophobia.substeps.domain.execution

import com.technophobia.substeps.domain.{Feature, Scenario}
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.{Mockito, Mock}
import org.junit.runner.RunWith
import org.junit.{Assert, Test}
import com.technophobia.substeps.domain.execution.RunResult.{Failed, Passed}


@RunWith(classOf[MockitoJUnitRunner])
class FeatureWithMultipleScenarioTest {

  @Mock
  var scenarioOne : Scenario = _

  @Mock
  var scenarioTwo : Scenario = _
  

  @Test
  def testRun() {

    Mockito.when(scenarioOne.run()).thenReturn(Passed)
    Mockito.when(scenarioTwo.run()).thenReturn(Passed)

    val feature = Feature("A feature", List(scenarioOne, scenarioTwo), Set())
    val passed = feature.run()

    Assert.assertEquals(Passed, passed)
    Mockito.verify(scenarioOne).run()
    Mockito.verify(scenarioTwo).run()
  }

  @Test
  def testWhenOneFailsTwoIsStillRun() {

    val failure = Failed("failure")

    Mockito.when(scenarioOne.run()).thenReturn(failure)
    Mockito.when(scenarioTwo.run()).thenReturn(Passed)

    val feature = Feature("A feature", List(scenarioOne, scenarioTwo), Set())
    val passed = feature.run()

    Assert.assertEquals(failure, passed)
    Mockito.verify(scenarioOne).run()
    Mockito.verify(scenarioTwo).run()
  }

  @Test
  def testWhenTwoFailsOneIsStillRun() {

    val failure = Failed("failure")

    Mockito.when(scenarioOne.run()).thenReturn(Passed)
    Mockito.when(scenarioTwo.run()).thenReturn(failure)

    val feature = Feature("A feature", List(scenarioOne, scenarioTwo), Set())
    val passed = feature.run()

    Assert.assertEquals(failure, passed)
    Mockito.verify(scenarioOne).run()
    Mockito.verify(scenarioTwo).run()
  }
}
