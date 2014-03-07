package com.technophobia.substeps


import org.junit.{Before, Test, Assert}
import _root_.com.technophobia.substeps.domain._
import com.technophobia.substeps.domain.repositories.SubstepRepository
import com.technophobia.substeps.parsing.FeatureFileParser

class FeatureFileParserTest extends FeatureFileParser(new SubstepRepository) with ParsingTestHelpers[Feature] {

  private val SIMPLE_FEATURE_FILE = "simple.feature"
  private val SCENARIO_OUTLINE_FEATURE_FILE = "scenario-outline.feature"
  private val SCENARIO_WITH_BACKGROUND_FILE = "scenario-with-background.feature"

  @Before
  def prepareSubsteps() {

    substepRepository.clear()
    substepRepository.add(WrittenSubstep("Given I think"))
    substepRepository.add(WrittenSubstep("Then I am"))

    substepRepository.add(WrittenSubstep("Given I think for <SECONDS>"))
    substepRepository.add(WrittenSubstep("Then I am <TIREDNESS_LEVEL>"))
    substepRepository.add(WrittenSubstep("So <SECONDS> means I'll be <TIREDNESS_LEVEL>"))

    substepRepository.add(WrittenSubstep("Given I clear down the data"))
    substepRepository.add(WrittenSubstep("And I rest all the counters"))
  }

  @Test
  def testSimpleFeature() {

    val feature = getSuccessfulParse(SIMPLE_FEATURE_FILE)

    Assert.assertEquals("A simple feature name", feature.name)
    Assert.assertEquals(Set("featureTag-1", "featureTag-2"), feature.tags)
    Assert.assertEquals(1, feature.scenarios.size)

    val scenario = feature.scenarios.head

    Assert.assertEquals(None, scenario.asInstanceOf[BasicScenario].background)

    Assert.assertEquals("A simple scenario name", scenario.title)
    Assert.assertEquals(Set("scenarioTag-1", "scenarioTag-2"), scenario.asInstanceOf[BasicScenario].tags)
    Assert.assertEquals(2, scenario.asInstanceOf[BasicScenario].steps.size)

    val substep1 = scenario.asInstanceOf[BasicScenario].steps(0).asInstanceOf[WrittenSubstepInvocation]
    val substep2 = scenario.asInstanceOf[BasicScenario].steps(1).asInstanceOf[WrittenSubstepInvocation]

    Assert.assertEquals("Given I think", substep1.invocationLine)
    Assert.assertEquals("Then I am", substep2.invocationLine)

  }

  @Test
  def testFeatureWithScenarioOutline() {

    val feature = getSuccessfulParse(SCENARIO_OUTLINE_FEATURE_FILE)

    Assert.assertEquals("A feature with a scenario outline", feature.name)
    Assert.assertTrue(feature.tags.isEmpty)
    Assert.assertEquals(1, feature.scenarios.size)

    val scenario = feature.scenarios.head

    def assertScenarioOutline(scenarioOutline: OutlinedScenario) {

    Assert.assertEquals("A scenario that's an outline", scenarioOutline.title)

    Assert.assertEquals(2, scenarioOutline.derivedScenarios.size)

    val firstExampleScenario = scenarioOutline.derivedScenarios(0)

    Assert.assertEquals(Set("scenarioOutlineTag"), firstExampleScenario.tags)
    val example1substep1 = firstExampleScenario.steps(0).asInstanceOf[WrittenSubstepInvocation]
    val example1substep2 = firstExampleScenario.steps(1).asInstanceOf[WrittenSubstepInvocation]
    val example1substep3 = firstExampleScenario.steps(2).asInstanceOf[WrittenSubstepInvocation]

    Assert.assertEquals("Given I think for 5", example1substep1.invocationLine)
    Assert.assertEquals("Then I am OK", example1substep2.invocationLine)
    Assert.assertEquals("So 5 means I'll be OK", example1substep3.invocationLine)

    val secondExampleScenario = scenarioOutline.derivedScenarios(1)

    Assert.assertEquals(Set("scenarioOutlineTag"), secondExampleScenario.tags)
    val example2substep1 = secondExampleScenario.steps(0).asInstanceOf[WrittenSubstepInvocation]
    val example2substep2 = secondExampleScenario.steps(1).asInstanceOf[WrittenSubstepInvocation]
    val example2substep3 = secondExampleScenario.steps(2).asInstanceOf[WrittenSubstepInvocation]

    Assert.assertEquals("Given I think for 120", example2substep1.invocationLine)
    Assert.assertEquals("Then I am EXHAUSTED", example2substep2.invocationLine)
    Assert.assertEquals("So 120 means I'll be EXHAUSTED", example2substep3.invocationLine)

    }

    scenario match {

      case scenarioOutline: OutlinedScenario => assertScenarioOutline(scenarioOutline)
      case x => throw new AssertionError("Scenario was of wrong type" + x.getClass.toString)

    }

  }

  @Test
  def scenarioWithBackground() {

    val feature = getSuccessfulParse(SCENARIO_WITH_BACKGROUND_FILE)
    val scenario = feature.scenarios.head.asInstanceOf[BasicScenario]
    Assert.assertEquals(2, scenario.steps)
    val background = scenario.background
    Assert.assertTrue(background.isDefined)
    Assert.assertEquals("This runs before the scenario", background.get.title)
    val backgroundSteps = background.get.steps
    Assert.assertEquals(2, backgroundSteps)
    val step1 = backgroundSteps(0).asInstanceOf[WrittenSubstep]
    val step2 = backgroundSteps(1).asInstanceOf[WrittenSubstep]
    Assert.assertEquals("Given I clear down the data", step1.signature)
    Assert.assertEquals("And I reset all the counters", step2.signature)
  }

  @Test
  def missingSubstepTest() {

    substepRepository.clear()

    val feature = getSuccessfulParse(SIMPLE_FEATURE_FILE)

    val scenario = feature.scenarios.head.asInstanceOf[BasicScenario]

    val steps = scenario.steps

    Assert.assertEquals(2, steps.size)
    steps.foreach(a => Assert.assertTrue(a.isInstanceOf[MissingSubstepInvocation]))

  }

}