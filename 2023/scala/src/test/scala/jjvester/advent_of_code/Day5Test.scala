package jjvester.advent_of_code

import org.junit.Assert

class Day5Test extends munit.FunSuite:

  test("sample 1") {
    val input = InputReader.readInput("src/test/resources/day5_sample")
    Assert.assertEquals(35, Day5.part1(input))
  }

//  test("part 1") {
//    val input = InputReader.readInput("src/test/resources/day5")
//    Assert.assertEquals(35, Day5.part1(input))
//  }

//  test("sample 2") {
//    val input = InputReader.readInput("src/test/resources/day5_sample")
//    Assert.assertEquals(46, Day5.part2(input))
//  }

//  test("part 2") {
//    val input = InputReader.readInput("src/test/resources/day5")
//    Assert.assertEquals(35, Day5.part2(input))
//  }