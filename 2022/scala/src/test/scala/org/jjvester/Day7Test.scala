package org.jjvester

import org.junit.Assert
import org.junit.Assert.assertTrue

import scala.collection.mutable

class Day7Test extends munit.FunSuite:

  private final val SAMPLE_INPUT_1 = List("$ cd /", "$ ls", "dir a", "14848514 b.txt", "8504156 c.dat", "dir d", "$ cd a", "$ ls", "dir e", "29116 f", "2557 g",
    "62596 h.lst", "$ cd e", "$ ls", "584 i", "$ cd ..", "$ cd ..", "$ cd d", "$ ls", "4060174 j", "8033020 d.log", "5626152 d.ext", "7214296 k")

  test("Sample_1") {
    Assert.assertEquals(95437, Day7().part1(SAMPLE_INPUT_1))
  }

  test("part1") {
    val input = InputReader.readInput("src/main/resources/day7/input")
    Assert.assertEquals(919137, Day7().part1(input))
  }

  test("Sample_1_part2") {
    Assert.assertEquals(24933642, Day7().part2(SAMPLE_INPUT_1))
  }

  test("part2") {
    val input = InputReader.readInput("src/main/resources/day7/input")
    Assert.assertEquals(2877389, Day7().part2(input))
  }