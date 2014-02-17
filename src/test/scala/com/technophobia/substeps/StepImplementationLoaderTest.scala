package com.technophobia.substeps

import com.technophobia.substeps.CodedSubstepLoader
import org.junit.{Assert, Test}

class StepImplementationLoaderTest {

  val EXPECTED_NUMBER_OF_STEPS = 3;

  @Test
  def testStepImplementationsFound() {

    val steps = CodedSubstepLoader.loadStepImplementations(List(this.getClass.getPackage.getName + ".implementations"))
    Assert.assertEquals(EXPECTED_NUMBER_OF_STEPS, steps.size)
  }

}
