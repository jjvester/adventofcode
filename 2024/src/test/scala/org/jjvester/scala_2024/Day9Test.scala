package org.jjvester.scala_2024

import scala.concurrent.duration.{Duration, FiniteDuration}

class Day9Test extends munit.FunSuite:

  override val munitTimeout: FiniteDuration = Duration(300, "s")

  test("sample 1 part 1") {
    assertEquals(new Day9().part1(InputReader.readInput("src/test/resources/day9/sample")), 1928L)
  }
  
  test("part 1") {
    assertEquals(new Day9().part1(InputReader.readInput("src/test/resources/day9/input")), 6307275788409L)
  }

  test("sample 1 part 2") {
    assertEquals(new Day9().part2(InputReader.readInput("src/test/resources/day9/sample")), 2858L)
  }

  test("part 2") {
    assertEquals(new Day9().part2(InputReader.readInput("src/test/resources/day9/input")), 6307275788409L)
  }
