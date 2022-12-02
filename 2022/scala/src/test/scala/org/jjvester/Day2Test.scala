package org.jjvester

import org.junit.Assert
import org.junit.Assert.assertTrue;

class Day2Test extends munit.FunSuite:

  test("Sample") {
    val input = List("A Y", "B X", "C Z")
    Assert.assertEquals(15, Day2.part1(input))
  }

  test("part1") {
    val input = InputReader.readInput("src/main/resources/day2/input")
    Assert.assertEquals(12_645, Day2.part1(input))
  }

  test("Sample2") {
    val input = List("A Y", "B X", "C Z")
    Assert.assertEquals(12, Day2.part2(input))
  }

  test("part2") {
    val input = InputReader.readInput("src/main/resources/day2/input")
    Assert.assertEquals(11756, Day2.part2(input))
  }
