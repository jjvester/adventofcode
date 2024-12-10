package org.jjvester.scala_2024

class Day10Test extends munit.FunSuite:
  test("sample 1 part 1") {
    assertEquals(new Day10().part1(InputReader.readInput("src/test/resources/day10/sample")), 36)
  }

  test("part 1") {
    assertEquals(new Day10().part1(InputReader.readInput("src/test/resources/day10/input")), 694)
  }

  test("sample 1 part 2") {
    assertEquals(new Day10().part2(InputReader.readInput("src/test/resources/day10/sample")), 81)
  }

  test("part 2") {
    assertEquals(new Day10().part2(InputReader.readInput("src/test/resources/day10/input")), 1497)
  }