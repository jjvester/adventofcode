package jjvester.advent_of_code

import scala.jdk.CollectionConverters._
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert

import java.util

class AdventOfCodePackageTest extends munit.FunSuite:

  test("adjacent") {
    assertThat(List(1,2).asJavaCollection).hasSameElementsAs(List(2, 1).asJavaCollection)
  }
