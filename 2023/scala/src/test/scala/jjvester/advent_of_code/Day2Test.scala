package jjvester.advent_of_code

import org.junit.Assert
import org.junit.Assert.assertTrue;

class Day2Test extends munit.FunSuite:

  test("sample 1") {
    val input = List("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green", "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
      "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red", "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
      "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

    Assert.assertEquals(8, new Day2(red = 12, green = 13, blue = 14).part1(input))
  }

  test("part 1") {
    val input = InputReader.readInput("src/test/resources/day2")
    Assert.assertEquals(2006, new Day2(red = 12, green = 13, blue = 14).part1(input))
  }

  test("sample 2") {
    val input = List("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green", "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
      "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red", "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
      "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

    Assert.assertEquals(2286, new Day2(red = 12, green = 13, blue = 14).part2(input))
  }

  test("part 2") {
    val input = InputReader.readInput("src/test/resources/day2")
    Assert.assertEquals(84911, new Day2(red = 12, green = 13, blue = 14).part2(input))
  }