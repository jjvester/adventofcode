package org.jjvester.scala_2024

import scala.annotation.tailrec

private[scala_2024] class Day11:

  private var cache: Map[(Int, String), Long] = Map.empty
  private def isEven(stone: String) = stone.forall(_.isDigit) && stone.length % 2 == 0
  private def splitStone(stone: String): List[String] = List(stone.substring(0, stone.length / 2).toLong.toString, stone.substring(stone.length / 2).toLong.toString)
  private def multiplyBy2024(stone: String) = List((stone.toLong * 2024).toString)

  private def replaceStone(stone: String): List[String] = stone match
    case "0" => List("1")
    case _ => if isEven(stone) then splitStone(stone) else multiplyBy2024(stone)

  @tailrec
  private def blink(times: Int, src: List[String]): List[String] =
    if times == 0 then src
    else blink(times - 1, src.flatMap(replaceStone))

  def part1(input: List[String]): Long =
    val stones = input.mkString.split(" ").toList
    val result = blink(25, stones)
    println(result.size)
    result.size

  def part2(input: List[String]): Long =
    val stones = input.mkString.split(" ").toList
    val one = "1"
    var count = 0L

    def split(times: Int, stones: List[String], prev: List[(Int, String)] = List.empty): Unit =
      if stones.size == 1 then blink(times, stones.head, prev)
      else
        val (l, r) = stones.splitAt(stones.size / 2)
        split(times, l, prev)
        split(times, r, prev)
    end split

    def blink(times: Int, stone: String, prev: List[(Int, String)]): Unit =
      if times == 0 then
        count += 1
        prev.foreach((prevTimes, prevStone) => cache = cache.updatedWith((prevTimes, prevStone))(mapping => Option(mapping.getOrElse(0L) + 1L)))
        //println(s"Hit 0 for stone $stone")
      else if cache.contains((times, stone)) then
        val occurrences = cache((times, stone))
        count += occurrences
        prev.foreach((prevTimes, prevStone) => cache = cache.updatedWith((prevTimes, prevStone))(mapping => Option(mapping.getOrElse(0L) + occurrences)))
        //println(s"cache hit for times $times and stone $stone")
      else
        val result = replaceStone(stone)
        split(times - 1, result, (times, stone) :: prev)
    end blink

    split(75, stones)
    count