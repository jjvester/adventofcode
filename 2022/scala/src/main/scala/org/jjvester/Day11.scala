package org.jjvester

import scala.annotation.tailrec
import scala.collection.mutable

final class Monkey(val id: String, var items: mutable.ListBuffer[Int], val op: Int => Int, val target: Int => String, var inspections: Int)

final class Day11:

  private def performRelief(worryLevel: Int, reliefLevel: Int): Int = Math.floor(worryLevel / reliefLevel).toInt

  @tailrec
  private def playMonkey(monkey: Monkey, items: mutable.ListBuffer[Int], monkeyMap: Map[String, Monkey]): Unit =
    if items.nonEmpty then
      val next = items.head

      val worryLevel = monkey.op(next)
      val bored = performRelief(worryLevel, 3)
      val nextMonkey = monkey.target(bored)
      monkeyMap(nextMonkey).items.addOne(bored)
      monkey.inspections += 1

      playMonkey(monkey, items.tail, monkeyMap)
    else
      monkey.items.clear()

  @tailrec
  private def playAllMonkeys(input: List[Monkey], monkeyMap: Map[String, Monkey]): Unit =
    if input.nonEmpty then
      val current = input.head
      playMonkey(current, current.items, monkeyMap)
      playAllMonkeys(input.tail, monkeyMap)

  @tailrec
  private def playRounds(input: List[Monkey], rounds: Int, monkeyMap: Map[String, Monkey]): List[Monkey] =
    if rounds > 19 then return input

    println("Round "+ rounds)
    input.foreach(monkey => println(monkey.id + " " + monkey.items))
    println("")
    playAllMonkeys(input, monkeyMap)
    playRounds(input, rounds + 1, monkeyMap)

  def part1(input: List[Monkey], rounds: Int): List[Monkey] = playRounds(input, rounds, input.map(monkey => (monkey.id, monkey)).toMap)

//  private def isMonkeyId(input: String)= input.startsWith("Monkey")
//  private def getMonkeyDetails(input: List[String], acc: List[Monkey], curr: Monkey): (List[String], List[Monkey]) =
//    if input.isEmpty || isMonkeyId(input.head.trim) then (input, curr :: acc)
//
//
//  private def getAllMonkeys(input: List[String], acc: List[Monkey]): List[Monkey] =
//    if input.isEmpty then acc
//
//    val
//
//  def part1(input: List[String], rounds: Int): Int =
//    def round
//
//    0
