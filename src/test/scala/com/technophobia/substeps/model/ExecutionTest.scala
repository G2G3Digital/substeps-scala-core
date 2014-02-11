package com.technophobia.substeps.model

import org.junit.{Before, Assert, Test}
import com.technophobia.substeps.model.execution.RunResult
import org.hamcrest.CoreMatchers._
import com.technophobia.substeps.repositories.SubstepRepository

class ExecutionTest {

  @Before
  def createSubsteps() {

    SubstepRepository.clear()
    val calculator = new Calculator
    val addStep = CodedSubstep( """ADD ([\d]+)""".r, classOf[Calculator].getMethod("add", classOf[Int]), calculator)
    val divideStep = CodedSubstep( """DIVIDE BY ([\d]+) ADD (.+) AND ROUND DOWN""".r, classOf[Calculator].getMethod("divideByAddAndRoundDown", classOf[Int], classOf[Double]), calculator)
    val resultStep = CodedSubstep( """Then I get ([\d]+)""".r, classOf[Calculator].getMethod("assertMemory", classOf[Int]), calculator)
    val mathsStep = WrittenSubstep("When I add <FIRST> to <SECOND> then divide by <THIRD>", "ADD <FIRST>", "ADD <SECOND>", "DIVIDE BY <THIRD> ADD 0.5 AND ROUND DOWN")

    SubstepRepository.add(addStep)
    SubstepRepository.add(divideStep)
    SubstepRepository.add(resultStep)
    SubstepRepository.add(mathsStep)
  }

  @Test
  def basicScenarioWhichShouldPassTest() {

    val scenario = BasicScenario("As a user I want to do some maths", List("When I add 3 to 5 then divide by 2", "Then I get 4"))
    Assert.assertEquals(RunResult.Passed, scenario.run())
  }

  @Test
  def basicScenarioWhichShouldFailTest() {

    val scenario = BasicScenario("As a user I want to do some maths", List("When I add 1 to 9 then divide by 2", "Then I get 6"))
    Assert.assertEquals(RunResult.Failed(List("expected:<6> but was:<5>")), scenario.run())
  }


  class Calculator {

    var memory: Int = 0

    def add(input: Int) { memory += input }
    def divideByAddAndRoundDown(divideBy: Int, add: Double) { memory = ((memory.asInstanceOf[Double] / divideBy) + add).asInstanceOf[Int]}
    def assertMemory(input: Int) {Assert.assertEquals(input, memory)}

  }
}
