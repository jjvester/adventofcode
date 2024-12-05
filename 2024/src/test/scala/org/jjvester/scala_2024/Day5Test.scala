package org.jjvester.scala_2024

class Day5Test extends munit.FunSuite:
  test("sample 1 part 1") {
    assertEquals(new Day5().part1(InputReader.readInput("src/test/resources/day5/sample")), 143)
  }

  test("part 1") {
    assertEquals(new Day5().part1(InputReader.readInput("src/test/resources/day5/input")), 4766)
  }

  test("sample 1 part 2") {
    assertEquals(new Day5().part2(InputReader.readInput("src/test/resources/day5/sample")), 123)
  }

  test("part 2") {
    assertEquals(new Day5().part2(InputReader.readInput("src/test/resources/day5/input")), 6257)
  }