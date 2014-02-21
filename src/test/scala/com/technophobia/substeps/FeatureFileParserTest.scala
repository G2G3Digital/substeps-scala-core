package com.technophobia.substeps


import org.junit.{Before, Test, Assert}
import _root_.com.technophobia.substeps.model._
import _root_.com.technophobia.substeps.repositories.SubstepRepository

class FeatureFileParserTest extends FeatureFileParser(new SubstepRepository) with ParsingTestHelpers[Feature] {

  private val SIMPLE_FEATURE_FILE = "simple.feature"
  private val SCENARIO_OUTLINE_FEATURE_FILE = "scenario-outline.feature"

  @Before
  def prepareSubsteps() {

    substepRepository.clear()
    substepRepository.add(WrittenSubstep("Given I think"))
    substepRepository.add(WrittenSubstep("Then I am"))

    substepRepository.add(WrittenSubstep("Given I think for <SECONDS>"))
    substepRepository.add(WrittenSubstep("Then I am <TIREDNESS_LEVEL>"))
    substepRepository.add(WrittenSubstep("So <SECONDS> means I'll be <TIREDNESS_LEVEL>"))
  }


  @Test
  def testSimpleFeature() {

    val feature = getSuccessfulParse(SIMPLE_FEATURE_FILE)

    Assert.assertEquals("A simple feature name", feature.name)
    Assert.assertEquals(Set("featureTag-1", "featureTag-2"), feature.tags)
    Assert.assertEquals(1, feature.scenarios.size)

    val scenario = feature.scenarios.head

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

}