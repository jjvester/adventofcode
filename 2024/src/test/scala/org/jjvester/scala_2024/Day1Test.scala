package org.jjvester.scala_2024

class Day1Test extends munit.FunSuite:
  test("sample 1 part 1") {
    assertEquals(Day1.part1(InputReader.readInput("src/test/resources/day1/sample")), 11)
  }

  test("part 1") {
    assertEquals(Day1.part1(InputReader.readInput("src/test/resources/day1/input")), 2192892)
  }

  test("sample 1 part 2") {
    assertEquals(Day1.part2(InputReader.readInput("src/test/resources/day1/sample")), 31L)
  }

  test("part 2") {
    assertEquals(Day1.part2(InputReader.readInput("src/test/resources/day1/input")), 22962826L)
  }
