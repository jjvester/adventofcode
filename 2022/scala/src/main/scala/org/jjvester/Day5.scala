package org.jjvester

import scala.annotation.tailrec
import scala.collection.mutable

object Day5:

  @tailrec
  private def transfer(amount: Int, origin: Int, dest: Int, crates: Map[Int, mutable.Stack[String]]): Unit =
    if amount > 0 then
      crates(dest).push(crates(origin).pop())
      transfer(amount - 1, origin, dest, crates)

  private def bulkTransfer(amount: Int, origin: Int, dest: Int, crates: Map[Int, mutable.Stack[String]]): Unit =
    @tailrec
    def recur(amount: Int, origin: Int, acc: List[String], crates: Map[Int, mutable.Stack[String]]): List[String] =
      if amount <= 0 then return acc
      recur(amount - 1, origin, crates(origin).pop() :: acc, crates)

    @tailrec
    def doBulkTransfer(src: List[String]): Unit =
      if src.nonEmpty then
        crates(dest).push(src.head)
        doBulkTransfer(src.tail)

    doBulkTransfer(recur(amount, origin, List.empty, crates))


  private def topOfCrates(crates: Map[Int, mutable.Stack[String]]): String =
    crates.map(kv => (kv._1, kv._2.head)).toList.sorted.map(pair => pair._2).mkString

  def part1(input: List[String], crates: Map[Int, mutable.Stack[String]]): String =
    @tailrec
    def recur(src: List[String]): Unit =
      if src.nonEmpty then
        val items = src.head.split(" ")
        transfer(Integer.valueOf(items(1)), Integer.valueOf(items(3)), Integer.valueOf(items(5)), crates)
        recur(src.tail)

    recur(input)
    topOfCrates(crates)

  def part2(input: List[String], crates: Map[Int, mutable.Stack[String]]): String =
    @tailrec
    def recur(src: List[String]): Unit =
      if src.nonEmpty then
        val items = src.head.split(" ")
        bulkTransfer(Integer.valueOf(items(1)), Integer.valueOf(items(3)), Integer.valueOf(items(5)), crates)
        recur(src.tail)

    recur(input)
    topOfCrates(crates)

