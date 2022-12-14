package org.jjvester

import org.junit.Assert
import org.junit.Assert.assertTrue

import scala.collection.mutable

class Day13Test extends munit.FunSuite:

  test("sample") {
    val input = InputReader.readInput("src/main/resources/day13/sample")
    Day13.part1(input)
  }

