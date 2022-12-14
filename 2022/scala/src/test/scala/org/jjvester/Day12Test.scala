package org.jjvester

import org.junit.Assert
import org.junit.Assert.assertTrue

import scala.collection.mutable

class Day12Test extends munit.FunSuite:

  test("sample_part1") {
    val input = InputReader.readInput("src/main/resources/day12/sample")
    Assert.assertEquals(31, Day12.part1(input))
  }

  test("part1") {
    val input = InputReader.readInput("src/main/resources/day12/input")
    Assert.assertEquals(534, Day12.part1(input))
  }

  test("sample_part2") {
    val input = InputReader.readInput("src/main/resources/day12/sample")
    Assert.assertEquals(29, Day12.part1(input))
  }

  test("part2") {
    val input = InputReader.readInput("src/main/resources/day12/input")
    Assert.assertEquals(525, Day12.part1(input))
  }

