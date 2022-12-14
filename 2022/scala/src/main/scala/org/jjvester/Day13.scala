package org.jjvester

import scala.annotation.tailrec

object Day13:
  private final class Pair(val left: String, val right: String, var ordered: Boolean)

  private def tokenize(input: String): Array[String] = input.split(",")
  private def toPair(input: List[String]) = Day13.Pair(input.tail.head, input.head, true)

  private def processToken(left: String, right: String): Unit = ??

  //private def checkPair(left: Array[String], right: Array[String], pair: Pair): Pair

  def part1(input: List[String]): Unit =
    @tailrec
    def recur(src: List[String], acc: List[String], pairs: List[Day13.Pair]): List[Day13.Pair] =
      if src.isEmpty then toPair(acc) :: pairs
      else if src.head.isEmpty then recur(src.tail, List.empty, toPair(acc) :: pairs)
      else recur(src.tail, src.head :: acc, pairs)

    @tailrec
    def ordered(pairs: List[Pair], acc: List[Pair]): List[Pair] =
      if pairs.isEmpty then acc
      else
        //TODO
        val left = tokenize(pairs.head.left)
        val right = tokenize(pairs.head.right)
        ordered(pairs.tail, acc)

    val pairs = recur(input, List.empty, List.empty)
    println(pairs)
