package org.jjvester

import scala.annotation.tailrec
import scala.collection.mutable

object Day10:

  private final val cycleChecks = mutable.Map[Int, Int](20 -> 0, 60 -> 0, 100 -> 0, 140 -> 0, 180 -> 0, 220 -> 0)

  private def isNoop(token: String) = "noop".equals(token)
  private def isAdd(token: String) = "addx".equals(token)
  private def parseInstruction(token: String): Array[String] = token.split(" ")
  private def updateCycleChecks(register: Int, cycle: Int) = if cycleChecks.contains(cycle) then cycleChecks.put(cycle, register)

  @tailrec
  private def add(ops: Array[String], register: Int, cycle: Int): (Int, Int) =
    updateCycleChecks(register, cycle)
    if ops.isEmpty then (register, cycle)
    else if isAdd(ops.head.trim) then add(ops.tail, register, cycle + 1)
    else add(ops.tail, Integer.valueOf(ops.head.trim) + register, cycle + 1)

  def part1(input: List[String]): Int =
    @tailrec
    def recur(src: List[String], register: Int, cycle: Int): Unit =
      if src.nonEmpty then
        val instruction = parseInstruction(src.head.trim)
        updateCycleChecks(register, cycle)
        if isNoop(instruction(0)) then recur(src.tail, register, cycle + 1)
        else
          val addResult = add(instruction, register, cycle)
          recur(src.tail, addResult._1, addResult._2)


    recur(input, 1, 1)
    cycleChecks.map(item => item._1 * item._2).sum
