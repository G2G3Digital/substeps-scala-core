package com.technophobia.substeps.model.execution

import com.technophobia.substeps.repositories.SubstepRepository
import com.technophobia.substeps.model.{OutlinedScenario, BasicScenario, WrittenSubstep, CodedSubstep}
import org.junit.{Assert, Test, Before}

class ScenarioExecutionTest {

  @Before
  def createSubsteps() {

    val calculator = new Calculator
    SubstepRepository.clear()
    val resetStep = CodedSubstep("Given my calculator is reset".r, classOf[Calculator].getMethod("reset"), calculator)
    val addStep = CodedSubstep( """ADD ([\d]+)""".r, classOf[Calculator].getMethod("add", classOf[Int]), calculator)
    val divideStep = CodedSubstep( """DIVIDE BY ([\d]+) ADD (.+) AND ROUND DOWN""".r, classOf[Calculator].getMethod("divideByAddAndRoundDown", classOf[Int], classOf[Double]), calculator)
    val resultStep = CodedSubstep( """Then I get ([\d]+)""".r, classOf[Calculator].getMethod("assertMemory", classOf[Int]), calculator)
    val mathsStep = WrittenSubstep("When I add <FIRST> to <SECOND> then divide by <THIRD>", "ADD <FIRST>", "ADD <SECOND>", "DIVIDE BY <THIRD> ADD 0.5 AND ROUND DOWN")

    SubstepRepository.add(resetStep)
    SubstepRepository.add(addStep)
    SubstepRepository.add(divideStep)
    SubstepRepository.add(resultStep)
    SubstepRepository.add(mathsStep)
  }

  @Test
  def basicScenarioWhichShouldPassTest() {

    val scenario = BasicScenario("As a user I want to do some maths", List("Given my calculator is reset", "When I add 3 to 5 then divide by 2", "Then I get 4"), Set())
    Assert.assertEquals(RunResult.Passed, scenario.run())
  }

  @Test
  def basicScenarioWhichShouldFailTest() {

    val scenario = BasicScenario("As a user I want to do some maths", List("Given my calculator is reset", "When I add 1 to 9 then divide by 2", "Then I get 6"), Set())
    Assert.assertEquals(RunResult.Failed(List("expected:<6> but was:<5>")), scenario.run())
  }

  @Test
  def outlineScenarioShouldPass() {

    val examples = List(Map("a" -> "3", "b" -> "5", "c" -> "4"),
                        Map("a" -> "7", "b" -> "3", "c" -> "5"))

    val outlineScenario = OutlinedScenario("As a user I want to repeatedly do maths",
        List("Given my calculator is reset", "When I add <a> to <b> then divide by 2", "Then I get <c>"), examples, Set())
    Assert.assertEquals(RunResult.Passed, outlineScenario.run())
  }

  class Calculator {

    var memory: Int = 0

    def reset() {memory = 0}
    def add(input: Int) { memory += input }
    def divideByAddAndRoundDown(divideBy: Int, add: Double) { memory = ((memory.asInstanceOf[Double] / divideBy) + add).asInstanceOf[Int]}
    def assertMemory(input: Int) {Assert.assertEquals(input, memory)}

  }
}
