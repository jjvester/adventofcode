package jjvester.advent_of_code

import org.junit.Assert
import org.junit.Assert.assertTrue;

class Day1Test extends munit.FunSuite:

  test("sample 1") {
    val input = List("1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet")
    Assert.assertEquals(142, Day1.part1(input))
  }

  test("part 1") {
    val input = InputReader.readInput("src/test/resources/day1")
    Assert.assertEquals(54605, Day1.part1(input))
  }

  test("sample 2") {
    val input = List("two1nine", "eightwothree", "abcone2threexyz", "xtwone3four", "4nineeightseven2", "zoneight234", "7pqrstsixteen")
    Assert.assertEquals(281, Day1.part2(input))
  }

  test("part 2") {
    val input = InputReader.readInput("src/test/resources/day1")
    Assert.assertEquals(55429, Day1.part2(input))
  }
