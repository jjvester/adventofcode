package jjvester.advent_of_code

import org.junit.Assert

class Day6Test extends munit.FunSuite:

  test("part1_sample 1") {
    val input = InputReader.readInput("src/test/resources/day6_sample")
    Assert.assertEquals(288, Day6.part1(input))
  }

  test("part1") {
    val input = InputReader.readInput("src/test/resources/day6")
    Assert.assertEquals(32076, Day6.part1(input))
  }

  test("part2_sample 1") {
    val input = InputReader.readInput("src/test/resources/day6_sample")
    Assert.assertEquals(71503, Day6.part2(input))
  }

  test("part2") {
    val input = InputReader.readInput("src/test/resources/day6")
    Assert.assertEquals(34278221, Day6.part2(input))
  }