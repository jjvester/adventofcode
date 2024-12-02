package org.jjvester.scala_2024

import scala.annotation.tailrec

private[scala_2024] object Day1:

  private def totalDistance(left: List[Int], right: List[Int]): Int = left.zip(right).map(route => Math.abs(route._1 - route._2)).sum

  @tailrec
  private def toLocations(input: List[String], left: List[Int] = Nil, right: List[Int] = Nil): (List[Int], List[Int]) =
    if input.nonEmpty then
      val locations = input.head.split(" {3}", 2)
      toLocations(input.tail, locations(0).toInt :: left, locations(1).toInt :: right)
    else  (left.sorted, right.sorted)

  def part1(input: List[String]): Int =
    val (left, right) = toLocations(input)
    totalDistance(left, right)

  def part2(input: List[String]): Long =
    val (left, right) = toLocations(input)
    left.map(leftLocation => right.groupBy(identity).getOrElse(leftLocation, Nil).size * leftLocation).sum