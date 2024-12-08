package org.jjvester.scala_2024

class Day7Test extends munit.FunSuite:
  test("sample 1 part 1") {
    assertEquals(Day7.part1(InputReader.readInput("src/test/resources/day7/sample")), 3749L)
  }

  test("part 1") {
    assertEquals(Day7.part1(InputReader.readInput("src/test/resources/day7/input")), 3598800864292L)
  }
