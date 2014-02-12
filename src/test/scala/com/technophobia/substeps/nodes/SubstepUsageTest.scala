package com.technophobia.substeps.nodes

import org.junit.{Before, Assert, Test}
import com.technophobia.substeps.repositories.SubstepRepository

class SubstepUsageTest {

  @Before
  def clearSubsteps() {

    SubstepRepository.clear()
  }

  @Test
  def testResolve() {

//    val substepUsage = UnresolvedSubstepUsage("Given I am")
//
//    SubstepNodeFactory.defineFromFile("Given I am", List(UnresolvedSubstepUsage("unresolvable")))
//
//    val resolved = substepUsage.resolve()
//    val expected = FileSubstepUsage("Given I am", List(UnresolvedSubstepUsage("unresolvable")))
//
//    Assert.assertEquals(expected, resolved)
  }

  @Test
  def testResolveNested() {

//    val substepUsage = UnresolvedSubstepUsage("Given I am")
//
//    SubstepNodeFactory.defineFromFile("Given I am", List(UnresolvedSubstepUsage("Given I look in the mirror"), UnresolvedSubstepUsage("And I am there")))
//    SubstepNodeFactory.defineFromFile("Given I look in the mirror", List(UnresolvedSubstepUsage("Look up"), UnresolvedSubstepUsage("And into the mirror")))
//
//    val resolved = substepUsage.resolve()
//    val expected = FileSubstepUsage("Given I am",
//                    List(FileSubstepUsage("Given I look in the mirror",
//                      List(UnresolvedSubstepUsage("Look up"), UnresolvedSubstepUsage("And into the mirror"))),
//                      UnresolvedSubstepUsage("And I am there")))
//
//    Assert.assertEquals(expected, resolved)

  }

  @Test
  def testResolveArguments() {

//    val substepUsage = UnresolvedSubstepUsage("Given I think for 2 minutes and 5 seconds")
//
//    SubstepNodeFactory.defineFromFile("Given I think for <MINUTES> minutes and <SECONDS> seconds", List(UnresolvedSubstepUsage("unresolvable")))
//
//    val resolved = substepUsage.resolve()
//    val expected = FileSubstepUsage("Given I think for 2 minutes and 5 seconds", List(UnresolvedSubstepUsage("unresolvable")))
//
//    Assert.assertEquals(expected, resolved)
  }

  @Test
  def testReplacedParametersAreAppliedToChildren() {

//    val substepUsage = UnresolvedSubstepUsage("Given I think for 500 seconds")
//
//    SubstepNodeFactory.defineFromFile("Given I think for <SECONDS> seconds",
//      List(UnresolvedSubstepUsage("That's something I'm not doing for <SECONDS> seconds"),
//        UnresolvedSubstepUsage("And I will never get that <SECONDS> seconds back")))
//
//    val resolved = substepUsage.resolve()
//    val expected = FileSubstepUsage("Given I think for 500 seconds",
//      List(UnresolvedSubstepUsage("That's something I'm not doing for 500 seconds"),
//        UnresolvedSubstepUsage("And I will never get that 500 seconds back")))
//
//    Assert.assertEquals(expected, resolved)
  }

  def aMethodIHaveImplemented() {}

  @Test
  def testResolveStepImplementation() {

//    val substepUsage = UnresolvedSubstepUsage("Given I have implemented something")
//
//    SubstepNodeFactory.defineFromSource("Given I have implemented something", this, classOf[SubstepUsageTest].getMethod("aMethodIHaveImplemented"))
//
//    val resolved = substepUsage.resolve()
//
//    resolved match {
//
//      case usage: ImplementationSubstepUsage => Assert.assertEquals("Given I have implemented something", usage.usageString)
//      case x => throw new AssertionError(s"Usage should have been a ${classOf[ImplementationSubstepUsage]} but was ${x}}")
//
//    }
  }
}
