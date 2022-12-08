package org.jjvester

import org.junit.Assert
import org.junit.Assert.assertTrue

import scala.collection.mutable

class Day8Test extends munit.FunSuite:

  private final val SAMPLE_INPUT_1 = List("30373", "25512", "65332", "33549", "35390")

  test("Sample_1") {
    Assert.assertEquals(21, Day8.part1(SAMPLE_INPUT_1))
  }

  test("part1") {
    val input = InputReader.readInput("src/main/resources/day8/input")
    Assert.assertEquals(1849, Day8.part1(input))
  }

  test("Sample_1_part2") {
    Assert.assertEquals(8, Day8.part2(SAMPLE_INPUT_1))
  }

  test("part2") {
    val input = InputReader.readInput("src/main/resources/day8/input")
    Assert.assertEquals(201600L, Day8.part2(input))
  }