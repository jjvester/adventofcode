package org.jjvester

import scala.annotation.tailrec

object Day3:
  private val priorities = Map("a" -> 1, "b" -> 2, "c" -> 3, "d" -> 4, "e" -> 5, "f" -> 6,
    "g" -> 7, "h" -> 8, "i" -> 9, "j" -> 10, "k" -> 11, "l" -> 12, "m" -> 13, "n" -> 14, "o" -> 15, "p" -> 16,
    "q" -> 17, "r" -> 18, "s" -> 19, "t" -> 20, "u" -> 21, "v" -> 22, "w" -> 23, "x" -> 24, "y" -> 25,
    "z" -> 26)

  private def priority(item: String): (String, Int) =
    if priorities.contains(item) then (item, priorities(item))
    else (item, priorities(item.toLowerCase) + 26)

  def part1(input: List[String]): List[(String, Int)] =
    @tailrec
    def duplicateItem(a: String, b: String): String =
      if b.contains(a.head) then a.head.toString
      else duplicateItem(a.tail, b)

    @tailrec
    def recur(src: List[String], acc: List[(String, Int)]): List[(String, Int)] =
      if src.isEmpty then return acc

      val items = src.head
      val (a, b) = items.splitAt(items.length / 2)
      val duplicate = duplicateItem(a.distinct, b)
      recur(src.tail, priority(duplicate) :: acc)

    recur(input, List.empty)

//  def part2(input: List[String]): List[(String)]