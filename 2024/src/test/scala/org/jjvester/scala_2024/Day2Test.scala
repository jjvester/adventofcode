package org.jjvester.scala_2024

class Day2Test extends munit.FunSuite:
  test("sample 1 part 1") {
    assertEquals(Day2.part1(InputReader.readInput("src/test/resources/day2/sample")), 2)
  }

  test("part 1") {
    assertEquals(Day2.part1(InputReader.readInput("src/test/resources/day2/input")), 218)
  }

//  test("sample 1 part 2") {
//    assertEquals(Day2.part2(InputReader.readInput("src/test/resources/day2/sample")), 4)
//  }
