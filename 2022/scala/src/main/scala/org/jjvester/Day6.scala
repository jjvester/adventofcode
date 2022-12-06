package org.jjvester

import scala.annotation.tailrec
import scala.collection.mutable

object Day6:
  private def firstUniquePartition(input: String, sliceIdx: Int): Int =
    var stack: mutable.Stack[String] = mutable.Stack(input.head.toString)

    @tailrec
    def recur(src: String, idx: Int): Int =
      if stack.distinct.size == sliceIdx then idx
      else if stack.size == sliceIdx then
        stack = stack.take(sliceIdx - 1)
        stack.push(src.head.toString)
        recur(src.tail, idx + 1)
      else
        stack.push(src.head.toString)
        recur(src.tail, idx + 1)

    recur(input.tail, 0) + 1

  def part1(input: String): Int = firstUniquePartition(input, 4)

  def part2(input: String): Int = firstUniquePartition(input, 14)