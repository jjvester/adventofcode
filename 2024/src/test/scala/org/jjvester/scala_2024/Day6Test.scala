package org.jjvester.scala_2024

class Day6Test extends munit.FunSuite:
  test("sample 1 part 1") {
    assertEquals(Day6.part1(InputReader.readInput("src/test/resources/day6/sample")), 41)
  }

  test("input") {
    assertEquals(Day6.part1(InputReader.readInput("src/test/resources/day6/input")), 5331)
  }

  test("sample 1 part 2") {
    assertEquals(Day6.part2(InputReader.readInput("src/test/resources/day6/sample")), 6)
  }

  test("input part 2") {
    assertEquals(Day6.part2(InputReader.readInput("src/test/resources/day6/input")), 1812)
  }