package com.technophobia.substeps


import org.junit.{Test, Assert}
import com.technophobia.substeps.model.{OutlinedScenario, Feature, BasicScenario}

class FeatureFileParserTest extends FeatureFileParser with ParsingTestHelpers[Feature] {

  private val SIMPLE_FEATURE_FILE = "simple.feature"
  private val SCENARIO_OUTLINE_FEATURE_FILE = "scenario-outline.feature"


  @Test
  def testSimpleFeature() {

    val feature = getSuccessfulParse(SIMPLE_FEATURE_FILE)

    Assert.assertEquals("A simple feature name", feature.name)
    Assert.assertEquals(List("featureTag-1", "featureTag-2"), feature.tags)
    Assert.assertEquals(1, feature.scenarios.size)

    val scenario = feature.scenarios.head

//    Assert.assertEquals("A simple scenario name", scenario.name)
//    Assert.assertEquals(List("scenarioTag-1", "scenarioTag-2"), scenario.asInstanceOf[BasicScenario].tags)
//    Assert.assertEquals(2, scenario.asInstanceOf[BasicScenario].steps.size)
//
//    val substep1 = scenario.asInstanceOf[BasicScenario].steps(0)
//    val substep2 = scenario.asInstanceOf[BasicScenario].steps(1)
//
//    Assert.assertEquals("Given I think", substep1.usageString)
//    Assert.assertEquals("Then I am", substep2.usageString)

  }

  @Test
  def testFeatureWithScenarioOutline() {

    val feature = getSuccessfulParse(SCENARIO_OUTLINE_FEATURE_FILE)

    Assert.assertEquals("A feature with a scenario outline", feature.name)
    Assert.assertTrue(feature.tags.isEmpty)
    Assert.assertEquals(1, feature.scenarios.size)

    val scenario = feature.scenarios.head

    def assertScenarioOutline(scenarioOutline: OutlinedScenario) {

//      Assert.assertEquals("A scenario that's an outline", scenarioOutline.name)
//
//      Assert.assertEquals(2, scenarioOutline.scenarios.size)
//
//      val firstExampleScenario = scenarioOutline.scenarios(0)
//
//      Assert.assertEquals(List("scenarioOutlineTag"), firstExampleScenario.tags)
//      val example1substep1 = firstExampleScenario.steps(0)
//      val example1substep2 = firstExampleScenario.steps(1)
//      val example1substep3 = firstExampleScenario.steps(2)
//
//      Assert.assertEquals("Given I think for 5", example1substep1.usageString)
//      Assert.assertEquals("Then I am OK", example1substep2.usageString)
//      Assert.assertEquals("So 5 means I'll be OK", example1substep3.usageString)
//
//      val secondExampleScenario = scenarioOutline.scenarios(1)
//
//      Assert.assertEquals(List("scenarioOutlineTag"), secondExampleScenario.tags)
//      val example2substep1 = secondExampleScenario.steps(0)
//      val example2substep2 = secondExampleScenario.steps(1)
//      val example2substep3 = secondExampleScenario.steps(2)
//
//      Assert.assertEquals("Given I think for 120", example2substep1.usageString)
//      Assert.assertEquals("Then I am EXHAUSTED", example2substep2.usageString)
//      Assert.assertEquals("So 120 means I'll be EXHAUSTED", example2substep3.usageString)

    }

    scenario match {

//      case scenarioOutline: ScenarioOutline => assertScenarioOutline(scenarioOutline)
      case x => throw new AssertionError("Scenario was of wrong type" + x.getClass.toString);

    }

  }

}