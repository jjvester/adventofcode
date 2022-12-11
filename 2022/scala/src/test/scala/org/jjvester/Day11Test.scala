package org.jjvester

import org.junit.Assert
import org.junit.Assert.assertTrue

import scala.collection.mutable

class Day11Test extends munit.FunSuite:

  test("part1") {
    val monkey0 = Monkey("0", mutable.ListBuffer(64, 89, 65, 95), x => x * 7, x => if x % 3 == 0 then "4" else "1", 0)
    val monkey1 = Monkey("1", mutable.ListBuffer(76, 66, 74, 87, 70, 56, 51, 66), x => x + 5, x => if x % 13 == 0 then "7" else "3", 0)
    val monkey2 = Monkey("2", mutable.ListBuffer(91, 60, 63), x => x * x, x => if x % 2 == 0 then "6" else "5", 0)
    val monkey3 = Monkey("3", mutable.ListBuffer(92, 61, 79, 97, 79), x => x + 6, x => if x % 11 == 0 then "2" else "6", 0)
    val monkey4 = Monkey("4", mutable.ListBuffer(93, 54), x => x * 11, x => if x % 5 == 0 then "1" else "7", 0)
    val monkey5 = Monkey("5", mutable.ListBuffer(60, 79, 92, 69, 88, 82, 70), x => x + 8, x => if x % 17 == 0 then "4" else "0", 0)
    val monkey6 = Monkey("6", mutable.ListBuffer(64, 57, 73, 89, 55, 53), x => x + 1, x => if x % 19 == 0 then "0" else "5", 0)
    val monkey7 = Monkey("7", mutable.ListBuffer(62), x => x + 4, x => if x % 7 == 0 then "3" else "2", 0)

    val sample = List(monkey0, monkey1, monkey2, monkey3, monkey4, monkey5, monkey6, monkey7)
    val monkeys = Day11().part1(sample, 0)
    monkeys.foreach(monkey => println(monkey.id + " " + monkey.inspections))
    println(monkeys.map(monkey => monkey.inspections.toLong).product)
  }
