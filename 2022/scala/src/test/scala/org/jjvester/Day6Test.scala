package org.jjvester

import org.junit.Assert
import org.junit.Assert.assertTrue

import scala.collection.mutable

class Day6Test extends munit.FunSuite:

  private final val SAMPLE_INPUT_1 = "bvwbjplbgvbhsrlpgdmjqwftvncz"
  private final val SAMPLE_INPUT_2 = "nppdvjthqldpwncqszvftbrmjlhg"
  private final val SAMPLE_INPUT_3 = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"

  private final val SAMPLE_INPUT_1_PART2 = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"

  test("Sample_1") {
    val firstSignalIndex = Day6.part1(SAMPLE_INPUT_1)
    Assert.assertEquals(5, firstSignalIndex)
  }

  test("Sample_2") {
    val firstSignalIndex = Day6.part1(SAMPLE_INPUT_2)
    Assert.assertEquals(6, firstSignalIndex)
  }

  test("Sample_3") {
    val firstSignalIndex = Day6.part1(SAMPLE_INPUT_3)
    Assert.assertEquals(10, firstSignalIndex)
  }

  test("part1") {
    val input = InputReader.readInput("src/main/resources/day6/input").mkString
    val firstSignalIndex = Day6.part1(input)
    Assert.assertEquals(1598, firstSignalIndex)
  }

  test("Sample_part2_1") {
    val firstSignalIndex = Day6.part2(SAMPLE_INPUT_1_PART2)
    Assert.assertEquals(26, firstSignalIndex)
  }

  test("part2") {
    val input = InputReader.readInput("src/main/resources/day6/input").mkString
    val firstSignalIndex = Day6.part2(input)
    Assert.assertEquals(2414, firstSignalIndex)
  }
