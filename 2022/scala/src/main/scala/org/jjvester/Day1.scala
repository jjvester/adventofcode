package org.jjvester

import scala.::
import scala.annotation.tailrec

object Day1:
  def part1(input: List[String]): Int =
    @tailrec
    def recur(src: List[String], curr: Int, max: Int): Int =
      if src.isEmpty then return max

      val item = src.head
      if item.isEmpty then
        val updated = if (max < curr) curr else max
        recur(src.tail, 0, updated)
      else recur(src.tail, curr + Integer.valueOf(item.trim()), max)

    recur(input, 0, 0)

  def part2(input: List[String]): List[Int] =
    @tailrec
    def recur(src: List[String], curr: Int, max: List[Int]): List[Int] =
      if src.isEmpty then return max

      val item = src.head
      if item.isEmpty then
        val sorted = max.sorted
        if sorted.size < 3 then recur(src.tail, 0, curr :: sorted)
        else if sorted.head < curr then recur(src.tail, 0, curr :: sorted.tail)
        else recur(src.tail, 0, max)
      else recur(src.tail, curr + Integer.valueOf(item), max)

    recur(input, 0, List.empty)

