package org.jjvester

import org.junit.Assert
import org.junit.Assert.assertTrue;

class Day3Test extends munit.FunSuite:

  test("Sample_1") {
    val input = List("vJrwpWtwJgWrhcsFMMfFFhFp", "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", "PmmdzqPrVvPwwTWBwg",
      "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", "ttgJtRGJQctTZtZT", "CrZsJsPPZsGzwwsLwLmpwMDw")
    Assert.assertEquals(157, Day3.part1(input).map(item => item._2).sum)
  }

  test("part1") {
    val input = InputReader.readInput("src/main/resources/day3/input")
    Assert.assertEquals(7878, Day3.part1(input).map(item => item._2).sum)
  }

  test("Sample_2") {
    val input = List("vJrwpWtwJgWrhcsFMMfFFhFp", "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", "PmmdzqPrVvPwwTWBwg",
      "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", "ttgJtRGJQctTZtZT", "CrZsJsPPZsGzwwsLwLmpwMDw")
    Assert.assertEquals(70, Day3.part2(input).map(item => item._2).sum)
  }

  test("part2") {
    val input = InputReader.readInput("src/main/resources/day3/input")
    Assert.assertEquals(2760, Day3.part2(input).map(item => item._2).sum)
  }