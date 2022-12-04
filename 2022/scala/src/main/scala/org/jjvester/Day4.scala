package org.jjvester

import scala.annotation.tailrec

object Day4:
  private def contains(a: (Int, Int), b: (Int, Int)): Boolean =
    if a._1 < b._1 then a._2 >= b._2
    else if b._1 < a._1 then b._2 >= a._2
    else a._1 == b._1

  private def extractPair(input: String): (Int, Int) =
    val start = Integer.valueOf(input.substring(0, input.indexOf('-')))
    val end = Integer.valueOf(input.substring(input.indexOf('-') + 1))
    (start, end)

  private def extractPairs(src: List[String]): ((Int, Int), (Int, Int)) =
    val pairs = src.head.split(",")
    (extractPair(pairs(0)), extractPair(pairs(1)))

  private def toSets(pair1: (Int, Int), pair2: (Int, Int)): (Set[Int], Set[Int]) =
    val a: Set[Int] = Set.range(pair1._1, pair1._2 + 1)
    val b: Set[Int] = Set.range(pair2._1, pair2._2 + 1)
    (a, b)

  def part1(input: List[String]): Int =
    @tailrec
    def recur(src: List[String], acc: Int): Int =
      if src.isEmpty then return acc

      val (pair1, pair2) = extractPairs(src)
      recur(src.tail, acc + (if contains(pair1, pair2) then 1 else 0))

    recur(input, 0)

  def part2(input: List[String]): Int =
    @tailrec
    def recur(src: List[String], acc: Int): Int =
      if src.isEmpty then return acc

      val (pair1, pair2) = extractPairs(src)
      val (a, b) = toSets(pair1, pair2)
      val intersect = if (a intersect b).nonEmpty then 1 else 0
      recur(src.tail, acc + intersect)

    recur(input, 0)