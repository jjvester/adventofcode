package org.jjvester.scala_2024

class Day11Test extends munit.FunSuite:
  test("sample 1 part 1") {
    assertEquals(new Day11().part1(InputReader.readInput("src/test/resources/day11/sample")), 55312L)
  }

  test("part 1") {
    assertEquals(new Day11().part1(InputReader.readInput("src/test/resources/day11/input")), 191690L)
  }

  test("part 2") {
    assertEquals(new Day11().part2(InputReader.readInput("src/test/resources/day11/input")), 228651922369703L)
  }