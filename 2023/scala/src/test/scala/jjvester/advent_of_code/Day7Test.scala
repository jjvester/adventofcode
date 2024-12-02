package jjvester.advent_of_code

import org.junit.Assert

class Day7Test extends munit.FunSuite:

  test("sample1") {
    val input = InputReader.readInput("src/test/resources/day7_sample1")
    Assert.assertEquals(6440, Day7().part1(input))
  }

  test("part1") {
    val input = InputReader.readInput("src/test/resources/day7")
    Assert.assertEquals(251058093, Day7().part1(input))
  }

  test("part2_sample1") {
    val input = InputReader.readInput("src/test/resources/day7_sample1")
    Assert.assertEquals(5905, Day7Part2().part2(input))
  }

  // 250245704 249998138 249781879
  test("part2") {
    val input = InputReader.readInput("src/test/resources/day7")
    Assert.assertEquals(249781879, Day7Part2().part2(input))
  }

  test("part2_sample2") {
    val input = InputReader.readInput("src/test/resources/day7_part2_sample2")
    Assert.assertEquals(6839, Day7Part2().part2(input))
  }