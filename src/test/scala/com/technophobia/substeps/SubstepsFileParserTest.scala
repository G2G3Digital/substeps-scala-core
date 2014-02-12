package com.technophobia.substeps

import org.junit.{Before, Assert, Test}

import com.technophobia.substeps.repositories.SubstepRepository
import com.technophobia.substeps.model.Substep

class SubstepsFileParserTest extends SubstepsFileParser with ParsingTestHelpers[List[Substep]]{

  private val SUBSTEPS_FILE = "simple.substeps"

  @Before
  def removeAllSubstepsFromCache() {

    SubstepRepository.clear()
  }

  @Test
  def testSubstepFile() {

    val substeps = getSuccessfulParse(SUBSTEPS_FILE)

    Assert.assertEquals(8, substeps.size)

    val firstSubstep = substeps.head

//    Assert.assertEquals("Given series of substeps is executed", firstSubstep.name)
//
//    def assertSubstepFileDefinition(fileSubstep: FileSubstep) {
//
//      Assert.assertEquals("When an event occurs", fileSubstep.substepUsages(0).usageString)
//      Assert.assertEquals("Then bad things happen", fileSubstep.substepUsages(1).usageString)
//      Assert.assertEquals("And people get upset", fileSubstep.substepUsages(2).usageString)
//    }
//
//    firstSubstep match {
//
//      case x:FileSubstep => assertSubstepFileDefinition(x)
//      case x => throw new AssertionError(x.toString)
//
//    }

  }
}
