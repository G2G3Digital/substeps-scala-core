package com.technophobia.substeps.model

import org.junit.{Assert, Test}
import com.technophobia.substeps.model.execution.RunResult
import org.hamcrest.CoreMatchers._
import com.technophobia.substeps.repositories.SubstepRepository

class ExecutionTest {

  @Test
  def basicScenarioTest() {

    val addStep = CodedSubstep("""ADD [\d]+""".r, Calculator.add(_))
    val divideStep = CodedSubstep("""DIVIDE BY [\d]+""".r, Calculator.divideBy(_))
    val resultStep = CodedSubstep("""Then I get [\d]+]""".r, Calculator.assertMemory(_))

    val mathsStep = WrittenSubstep("When I add <FIRST> to <SECOND> then divide by <THIRD>", List("ADD <FIRST>", "ADD <SECOND>", "DIVIDE BY <THIRD>"));

    SubstepRepository.add(addStep)
    SubstepRepository.add(divideStep)
    SubstepRepository.add(resultStep)
    SubstepRepository.add(mathsStep)

    val scenario = BasicScenario("As a user I want to do some maths", List("When I add 3 to 5 then divide by 2", "Then I get 4"))

    Assert.assertThat(scenario.run(), is(RunResult.Passed))
  }

  object Calculator {

    var memory: Int = 0

    def add(input: Int) { memory += input }
    def divideBy(input: Int) { memory /= input}
    def assertMemory(input: Int) {Assert.assertEquals(memory, input)}

  }

}
