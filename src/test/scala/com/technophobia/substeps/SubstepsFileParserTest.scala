package com.technophobia.substeps

import org.junit.{Assert, Test}

import _root_.com.technophobia.substeps.model.{WrittenSubstep, Substep}

class SubstepsFileParserTest extends SubstepsFileParser with ParsingTestHelpers[List[Substep]]{

  private val SUBSTEPS_FILE = "simple.substeps"

  @Test
  def testSubstepFile() {

    val substeps = getSuccessfulParse(SUBSTEPS_FILE)

    Assert.assertEquals(8, substeps.size)

    val firstSubstep = substeps.head

    Assert.assertEquals("^Given series of substeps is executed$", firstSubstep.regex.toString())

    def assertSubstepFileDefinition(writtenSubstep: WrittenSubstep) {

      Assert.assertEquals("When an event occurs", writtenSubstep.invocationLines(0))
      Assert.assertEquals("Then bad things happen", writtenSubstep.invocationLines(1))
      Assert.assertEquals("And people get upset", writtenSubstep.invocationLines(2))
    }

    firstSubstep match {

      case x:WrittenSubstep => assertSubstepFileDefinition(x)
      case x => throw new AssertionError(x.toString)

    }

  }
}
