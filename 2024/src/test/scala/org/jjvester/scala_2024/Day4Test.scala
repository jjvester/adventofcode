package org.jjvester.scala_2024

class Day4Test extends munit.FunSuite:
  test("sample 1 part 1") {
    assertEquals(Day4.part1(InputReader.readInput("src/test/resources/day4/sample")), 18)
  }

  test("part 1") {
    assertEquals(Day4.part1(InputReader.readInput("src/test/resources/day4/input")), 2573)
  }

  test("sample 1 part 2") {
    assertEquals(Day4.part2(InputReader.readInput("src/test/resources/day4/sample")), 9)
  }

  test("part 2") {
    assertEquals(Day4.part2(InputReader.readInput("src/test/resources/day4/input")), 1850)
  }