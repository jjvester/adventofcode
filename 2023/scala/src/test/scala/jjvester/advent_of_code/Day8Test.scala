package jjvester.advent_of_code

import org.junit.Assert

class Day8Test extends munit.FunSuite:

  test("part 1 sample 1") {
    val input = InputReader.readInput("src/test/resources/day8_part1_sample1")
    Assert.assertEquals(2, new Day8(input).part1())
  }

  test("part 1 sample 2") {
    val input = InputReader.readInput("src/test/resources/day8_part1_sample2")
    Assert.assertEquals(6, new Day8(input).part1())
  }

  test("part 1") {
    val input = InputReader.readInput("src/test/resources/day8")
    Assert.assertEquals(15517, new Day8(input).part1())
  }

  test("part 2 sample 2") {
    val input = InputReader.readInput("src/test/resources/day8_part2_sample1")
    Assert.assertEquals(6, new Day8(input).part2())
  }

  test("part 2") {
    val input = InputReader.readInput("src/test/resources/day8")
    Assert.assertEquals(6, new Day8(input).part2())
  }