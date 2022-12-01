package org.jjvester

import org.junit.Assert
import org.junit.Assert.assertTrue;

class Day1Test extends munit.FunSuite:

  test("Sample") {
    val input = List("1000", "", "2000", "3000", "", "4000", "", "5000", "6000", "", "7000", "8000", "9000", "", "10000")
    Assert.assertEquals(24_000, Day1.part1(input))
  }

  test("part1") {
    val input = InputReader.readInput("src/main/resources/day1/input")
    Assert.assertEquals(69_912, Day1.part1(input))
  }

  test("part2") {
    val input = InputReader.readInput("src/main/resources/day1/input")
    Assert.assertEquals(208_180, Day1.part2(input).sum)
  }
