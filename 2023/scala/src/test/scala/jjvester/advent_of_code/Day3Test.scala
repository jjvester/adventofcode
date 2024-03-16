package jjvester.advent_of_code

import org.junit.Assert

class Day3Test extends munit.FunSuite:

  test("part1_sample 1") {
    val input = List("467..114..", "...*......", "..35..633.", "......#...", "617*......", ".....+.58.", "..592.....", "......755.", "...$.*....", ".664.598..")
    Assert.assertEquals(4361, new Day3(10, 10).part1(input))
  }

  test("part1_day3_sample1") {
    val input = InputReader.readInput("src/test/resources/day3_sample1")
    Assert.assertEquals(413, new Day3(input.head.length, input.size).part1(input))
  }

  test("part 1") {
    val input = InputReader.readInput("src/test/resources/day3")
    Assert.assertEquals(544664, new Day3(input.head.length, input.size).part1(input))
  }

  test("part2_day3_sample2") {
    val input = InputReader.readInput("src/test/resources/day3_sample2")
    Assert.assertEquals(467835, new Day3(input.head.length, input.size).part2(input))
  }

  test("part 2") {
    val input = InputReader.readInput("src/test/resources/day3")
    Assert.assertEquals(84495585, new Day3(input.head.length, input.size).part2(input))
  }