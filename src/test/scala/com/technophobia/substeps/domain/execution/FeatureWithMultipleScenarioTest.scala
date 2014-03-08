package com.technophobia.substeps.domain.execution

import com.technophobia.substeps.domain.{Background, Feature, Scenario}
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

  @Mock
  var background : Background = _

  @Test
  def testRun() {

    Mockito.when(scenarioOne.run()).thenReturn(Passed)
    Mockito.when(scenarioTwo.run()).thenReturn(Passed)

    val feature = Feature("A feature", None, List(scenarioOne, scenarioTwo), Set())
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

    val feature = Feature("A feature", None, List(scenarioOne, scenarioTwo), Set())
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

    val feature = Feature("A feature", None, List(scenarioOne, scenarioTwo), Set())
    val passed = feature.run()

    Assert.assertEquals(failure, passed)
    Mockito.verify(scenarioOne).run()
    Mockito.verify(scenarioTwo).run()
  }

  @Test
  def backgroundPassesAndScenariosRun() {

    Mockito.when(background.run()).thenReturn(Passed)
    Mockito.when(scenarioOne.run()).thenReturn(Passed)
    Mockito.when(scenarioTwo.run()).thenReturn(Passed)


    val feature = Feature("A feature with a background", Some(background), List(scenarioOne, scenarioTwo), Set())

    val result = feature.run()

    Assert.assertEquals(RunResult.Passed, result)
    Mockito.verify(scenarioOne).run()
    Mockito.verify(scenarioTwo).run()
    Mockito.verify(background, Mockito.times(2)).run()
  }


}
