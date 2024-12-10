package org.jjvester.scala_2024

import scala.concurrent.duration.{Duration, FiniteDuration}

class Day9Test extends munit.FunSuite:

  override val munitTimeout: FiniteDuration = Duration(300, "s")

  test("sample 1 part 1") {
    assertEquals(new Day9().part1(InputReader.readInput("src/test/resources/day9/sample")), 1928)
  }

  // 768893376
  // 768893376
  // 768893376
//  test("part 1") {
//    assertEquals(new Day9().part1(InputReader.readInput("src/test/resources/day9/input")), 1928)
//  }