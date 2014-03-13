package com.technophobia.substeps.domain

import org.junit.{Assert, Test}

class TagCheckerTest {

  @Test
  def testFromInclusions() {

    val inclusionChecker = TagChecker.fromInclusions(Set("include1", "include2"))

    Assert.assertTrue(inclusionChecker.shouldRunFor(Set("include1", "include2")))
    Assert.assertTrue(inclusionChecker.shouldRunFor(Set("include1")))
    Assert.assertTrue(inclusionChecker.shouldRunFor(Set("include2")))
    Assert.assertTrue(inclusionChecker.shouldRunFor(Set("include1", "other")))
    Assert.assertFalse(inclusionChecker.shouldRunFor(Set()))
    Assert.assertFalse(inclusionChecker.shouldRunFor(Set("other", "other2")))
  }

  @Test
  def testFromExclusions() {

    val exclusionChecker = TagChecker.fromExclusions(Set("exclude1", "exclude2"))

    Assert.assertFalse(exclusionChecker.shouldRunFor(Set("exclude1", "exclude2")))
    Assert.assertFalse(exclusionChecker.shouldRunFor(Set("exclude1")))
    Assert.assertFalse(exclusionChecker.shouldRunFor(Set("exclude2")))
    Assert.assertFalse(exclusionChecker.shouldRunFor(Set("exclude1", "other")))
    Assert.assertTrue(exclusionChecker.shouldRunFor(Set()))
    Assert.assertTrue(exclusionChecker.shouldRunFor(Set("other", "other2")))
  }

  @Test
  def testFromAll() {

    val runAllChecker = TagChecker.runAll

    Assert.assertTrue(runAllChecker.shouldRunFor(Set()))
    Assert.assertTrue(runAllChecker.shouldRunFor(Set("something")))
    Assert.assertTrue(runAllChecker.shouldRunFor(Set("something", "somethingElse")))
  }
}
