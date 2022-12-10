package org.jjvester

import org.junit.Assert
import org.junit.Assert.assertTrue

import scala.collection.mutable

class Day10Test extends munit.FunSuite:

  test("Sample") {
    val input = InputReader.readInput("src/main/resources/day10/sample")
    Assert.assertEquals(13140, Day10.part1(input))
  }

  test("part1") {
    val input = InputReader.readInput("src/main/resources/day10/input")
    //Assert.assertEquals(13140, Day10.part1(input))
    println(Day10.part1(input))
  }

  test("Sample_part2") {
    val input = InputReader.readInput("src/main/resources/day10/sample")
    Day10Part2.part1(input)
  }

  test("part2") {
    val input = InputReader.readInput("src/main/resources/day10/input")
    Day10Part2.part1(input)
  }
