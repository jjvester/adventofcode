package org.jjvester

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Day10Part2:

  private final val cycleChecks = mutable.Map[Int, Int](20 -> 0, 60 -> 0, 100 -> 0, 140 -> 0, 180 -> 0, 220 -> 0)
  private def isNoop(token: String) = "noop".equals(token)
  private def isAdd(token: String) = "addx".equals(token)
  private def parseInstruction(token: String): Array[String] = token.split(" ")
  private def updateCycleChecks(register: Int, cycle: Int) = if cycleChecks.contains(cycle) then cycleChecks.put(cycle, register)

  private val crt: mutable.ListBuffer[List[String]] = ListBuffer()
  private val row: mutable.ListBuffer[String] = ListBuffer()

  private def isOverlapping(register: Int, idx: Int) = idx == register || idx == (register - 1) || (idx == register + 1)

  private def draw(register: Int, cycle: Int): Unit =
    var current = row.size
    if current == 40 then
      crt.addOne(row.toList)
      row.clear()
      current = 0

    if isOverlapping(register, current) then row.insert(current, "#")
    else row.insert(current, ".")

    //println("Drawing for cycle " + cycle)

  @tailrec
  private def add(ops: Array[String], register: Int, cycle: Int): (Int, Int) =
    updateCycleChecks(register, cycle)
    if ops.isEmpty then (register, cycle)
    else if isAdd(ops.head.trim) then
      draw(register, cycle + 1)
      add(ops.tail, register, cycle + 1)
    else
      add(ops.tail, Integer.valueOf(ops.head.trim) + register, cycle + 1)

  def part1(input: List[String]): Unit =
    @tailrec
    def recur(src: List[String], register: Int, cycle: Int): Unit =
      draw(register, cycle)
      if src.nonEmpty then
        val instruction = parseInstruction(src.head.trim)
        updateCycleChecks(register, cycle)
        if isNoop(instruction(0)) then
          recur(src.tail, register, cycle + 1)
        else
          val addResult = add(instruction, register, cycle)
          recur(src.tail, addResult._1, addResult._2)

    recur(input, 1, 1)
    crt.foreach(row =>
      row.foreach(item => print(item + " "))
      println("")
    )
//    println(crt)
