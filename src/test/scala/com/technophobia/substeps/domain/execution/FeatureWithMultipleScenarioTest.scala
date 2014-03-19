package com.technophobia.substeps.domain.execution

import com.technophobia.substeps.domain._
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.{Matchers, Spy, Mockito, Mock}
import org.junit.runner.RunWith
import org.junit.{Assert, Test}
import com.technophobia.substeps.domain.execution.RunResult.{Failed, Passed}
import com.technophobia.substeps.domain.Feature
import scala.Some


@RunWith(classOf[MockitoJUnitRunner])
class FeatureWithMultipleScenarioTest {

  @Spy
  var scenarioOne = BasicScenario(null,Seq(),Set())

  @Spy
  var scenarioTwo = BasicScenario(null,Seq(),Set())

  @Mock
  var background : Background = _

  @Test
  def testRun() {

    Mockito.doReturn(RunResult.Passed).when(scenarioOne).run()
    Mockito.doReturn(RunResult.Passed).when(scenarioTwo).run()

    val feature = Feature("A feature", None, List(scenarioOne, scenarioTwo), Set())
    val passed = feature.run(TagChecker.fromExclusions(Set()))

    Assert.assertEquals(Passed, passed)
    Mockito.verify(scenarioOne).run()
    Mockito.verify(scenarioTwo).run()
  }

  @Test
  def testWhenOneFailsTwoIsStillRun() {

    val failure = Failed("failure")

    Mockito.doReturn(failure).when(scenarioOne).run()
    Mockito.doReturn(RunResult.Passed).when(scenarioTwo).run()

    val feature = Feature("A feature", None, List(scenarioOne, scenarioTwo), Set())
    val passed = feature.run(TagChecker.fromExclusions(Set()))

    Assert.assertEquals(failure, passed)
    Mockito.verify(scenarioOne).run()
    Mockito.verify(scenarioTwo).run()
  }

  @Test
  def testWhenTwoFailsOneIsStillRun() {

    val failure = Failed("failure")

    Mockito.doReturn(RunResult.Passed).when(scenarioOne).run()
    Mockito.doReturn(failure).when(scenarioTwo).run()

    val feature = Feature("A feature", None, List(scenarioOne, scenarioTwo), Set())
    val passed = feature.run(TagChecker.fromExclusions(Set()))

    Assert.assertEquals(failure, passed)
    Mockito.verify(scenarioOne).run()
    Mockito.verify(scenarioTwo).run()
  }

  @Test
  def backgroundPassesAndScenariosRun() {

    Mockito.when(background.run()).thenReturn(Passed)
    Mockito.doReturn(RunResult.Passed).when(scenarioOne).run()
    Mockito.doReturn(RunResult.Passed).when(scenarioTwo).run()


    val feature = Feature("A feature with a background", Some(background), List(scenarioOne, scenarioTwo), Set())

    val result = feature.run(TagChecker.fromExclusions(Set()))

    Assert.assertEquals(RunResult.Passed, result)
    Mockito.verify(scenarioOne).run()
    Mockito.verify(scenarioTwo).run()
    Mockito.verify(background, Mockito.times(2)).run()
  }


}
