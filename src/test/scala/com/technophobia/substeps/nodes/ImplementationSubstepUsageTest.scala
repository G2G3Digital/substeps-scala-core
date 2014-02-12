package com.technophobia.substeps.nodes

import org.junit.{Assert, Test}

class ImplementationSubstepUsageTest {

  var simpleMethodInvoked = false
  def simpleMethod = simpleMethodInvoked = true

  @Test
  def testSimpleInvocation() {

//    SubstepNodeFactory.defineFromSource("Given I have defined a simple method", this, classOf[ImplementationSubstepUsageTest].getMethod("simpleMethod"))
//
//    val unresolved = UnresolvedSubstepUsage("Given I have defined a simple method")
//
//    val resolved = unresolved.resolve()
//
//    Assert.assertFalse(simpleMethodInvoked)
//    resolved.asInstanceOf[ImplementationSubstepUsage].invocation()
//    Assert.assertTrue(simpleMethodInvoked)
  }

  var stringArg: String = _
  def methodWithStringArg(arg: String) = stringArg = arg

  @Test
  def testMethodWithAStringArgument() {

//    SubstepNodeFactory.defineFromSource("Given I have defined a method with a string argument and I pass (.*)", this, classOf[ImplementationSubstepUsageTest].getMethod("methodWithStringArg", classOf[String]))
//
//    val unresolved = UnresolvedSubstepUsage("Given I have defined a method with a string argument and I pass hello")
//
//    val resolved = unresolved.resolve()
//
//    Assert.assertNull(stringArg)
//    resolved.asInstanceOf[ImplementationSubstepUsage].invocation()
//    Assert.assertEquals("hello", stringArg)
//
  }

  var integerArg: Integer = _
  def methodWithIntegerArg(arg: Integer) = integerArg = arg

  @Test
  def testMethodWithAnIntegerArg() {

//    SubstepNodeFactory.defineFromSource("Given I have defined a method with an integer argument and I pass (.*)", this, classOf[ImplementationSubstepUsageTest].getMethod("methodWithIntegerArg", classOf[Integer]))
//
//    val unresolved = UnresolvedSubstepUsage("Given I have defined a method with an integer argument and I pass 4")
//
//    val resolved = unresolved.resolve()
//
//    Assert.assertNull(integerArg)
//    resolved.asInstanceOf[ImplementationSubstepUsage].invocation()
//    Assert.assertEquals(4, integerArg)
  }

  var doubleArg: Double = _
  def methodWithDoubleArg(arg: Double) = doubleArg = arg

  @Test
  def testMethodWithAnDoubleArg() {

//    SubstepNodeFactory.defineFromSource("Given I have defined a method with an double argument and I pass (.*)", this, classOf[ImplementationSubstepUsageTest].getMethod("methodWithDoubleArg", classOf[Double]))
//
//    val unresolved = UnresolvedSubstepUsage("Given I have defined a method with an double argument and I pass 4.6")
//
//    val resolved = unresolved.resolve()
//
//    Assert.assertNull(doubleArg)
//    resolved.asInstanceOf[ImplementationSubstepUsage].invocation()
//    Assert.assertEquals(4.6, doubleArg, 0)
  }

  var longArg: Long = _
  def methodWithLongArg(arg: Long) = longArg = arg

  @Test
  def testMethodWithAnLongArg() {

//    SubstepNodeFactory.defineFromSource("Given I have defined a method with an long argument and I pass (.*)", this, classOf[ImplementationSubstepUsageTest].getMethod("methodWithLongArg", classOf[Long]))
//
//    val unresolved = UnresolvedSubstepUsage("Given I have defined a method with an long argument and I pass 469343345")
//
//    val resolved = unresolved.resolve()
//
//    Assert.assertNull(longArg)
//    resolved.asInstanceOf[ImplementationSubstepUsage].invocation()
//    Assert.assertEquals(469343345, longArg)
  }

}
