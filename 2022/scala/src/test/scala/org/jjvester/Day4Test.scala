package org.jjvester

import org.junit.Assert
import org.junit.Assert.assertTrue;

class Day4Test extends munit.FunSuite:

  test("Sample_1") {
    val input = List("2-4,6-8", "2-3,4-5", "5-7,7-9", "2-8,3-7", "6-6,4-6", "2-6,4-8")
    Assert.assertEquals(2, Day4.part1(input))
  }

  test("part1") {
    val input = InputReader.readInput("src/main/resources/day4/input")
    Assert.assertEquals(450, Day4.part1(input))
  }

  test("Sample_2") {
    val input = List("2-4,6-8", "2-3,4-5", "5-7,7-9", "2-8,3-7", "6-6,4-6", "2-6,4-8")
    Assert.assertEquals(4, Day4.part2(input))
  }

  test("part2") {
    val input = InputReader.readInput("src/main/resources/day4/input")
    Assert.assertEquals(837, Day4.part2(input))
  }