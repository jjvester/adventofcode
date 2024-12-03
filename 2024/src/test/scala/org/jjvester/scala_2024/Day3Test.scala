package org.jjvester.scala_2024

class Day3Test extends munit.FunSuite:
  test("sample 1 part 1") {
    assertEquals(Day3.part1(InputReader.readInput("src/test/resources/day3/sample")), 161L)
  }

  test("part 1") {
    assertEquals(Day3.part1(InputReader.readInput("src/test/resources/day3/input")), 183669043L)
  }

  test("sample 2 part 2") {
    assertEquals(Day3.part2(InputReader.readInput("src/test/resources/day3/sample2")), 48L)
  }
  test("part 2") {
    assertEquals(Day3.part2(InputReader.readInput("src/test/resources/day3/input")), 59097164L)
  }