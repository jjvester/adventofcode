package org.jjvester

import org.junit.Assert
import org.junit.Assert.assertTrue

import scala.collection.mutable

class Day9Test extends munit.FunSuite:

  private final val SAMPLE_INPUT_1 = List("R 4", "U 4", "L 3", "D 1", "R 4", "D 1", "L 5", "R 2")
  private final val SAMPLE_INPUT_2 = List("R 5", "U 8", "L 8", "D 3", "R 17", "D 10", "L 25", "U 20")

  test("Sample_1") {
    Assert.assertEquals(13, Day9.part1(SAMPLE_INPUT_1))
  }

  test("part1") {
    val input = InputReader.readInput("src/main/resources/day9/input")
    Assert.assertEquals(6357, Day9.part1(input))
  }

  test("Sample_2") {
    Assert.assertEquals(36, Day9.part2(SAMPLE_INPUT_2, Day9.Point(11, 5)))
  }

  test("part2") {
    val input = InputReader.readInput("src/main/resources/day9/input")
    Assert.assertEquals(2627, Day9.part2(input, Day9.Point(11, 5)))
  }
