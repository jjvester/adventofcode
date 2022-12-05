package org.jjvester

import org.junit.Assert
import org.junit.Assert.assertTrue

import scala.collection.mutable

class Day5Test extends munit.FunSuite:

  private final val SAMPLE_INPUT: List[String] = List("move 1 from 2 to 1", "move 3 from 1 to 3", "move 2 from 2 to 1", "move 1 from 1 to 2")

  private def sampleCrates() = Map(1 -> mutable.Stack("N", "Z"),
    2 -> mutable.Stack("D", "C", "M"),
    3 -> mutable.Stack("P"))

  private def crates() = Map(1 -> mutable.Stack("J", "Z", "G", "V", "T", "D", "B", "N"),
    2 -> mutable.Stack("F", "P", "W", "D", "M", "R", "S"),
    3 -> mutable.Stack("Z", "S", "R", "C", "V"),
    4 -> mutable.Stack("G", "H", "P", "Z", "J", "T", "R"),
    5 -> mutable.Stack("F", "Q", "Z", "D", "N", "J", "C", "T"),
    6 -> mutable.Stack("M", "F", "S", "G", "W", "P", "V", "N"),
    7 -> mutable.Stack("Q", "P", "B", "V", "C", "G"),
    8 -> mutable.Stack("N", "P", "B", "Z"),
    9 -> mutable.Stack("J", "P", "W"))


  test("Sample_1") {
    val topOfCrates = Day5.part1(SAMPLE_INPUT, sampleCrates())
    Assert.assertEquals("CMZ", topOfCrates)
  }

  test("part1") {
    val input = InputReader.readInput("src/main/resources/day5/input")
    Assert.assertEquals("GFTNRBZPF", Day5.part1(input, crates()))
  }

  test("Sample_2") {
    val topOfCrates = Day5.part2(SAMPLE_INPUT, sampleCrates())
    Assert.assertEquals("MCD", topOfCrates)
  }

  test("part2") {
    val input = InputReader.readInput("src/main/resources/day5/input")
    Assert.assertEquals("VRQWPDSGP", Day5.part2(input, crates()))
  }