package jjvester.advent_of_code

import scala.annotation.tailrec

private[advent_of_code] class Day8(input: List[String]):

  private val route = input.head
  private val wasteLand = plotWasteland(input.tail.tail)
  private val start = "AAA"

  // AAA = (BBB, CCC)
  private def addWastelandEntry(input: String) =
    val parts = input.split("=")
    val valueParts = parts.tail.head.split(",").map(_.replaceAll("([()])","").trim)
    
    val key = parts.head.trim
    (key, valueParts.head, valueParts.tail.head)

  @tailrec
  private def plotWasteland(input: List[String], collector: Map[String, (String, String)] = Map.empty): Map[String, (String, String)] =
    if input.isEmpty then collector
    else
      val entry = addWastelandEntry(input.head)
      plotWasteland(input.tail, collector.updated(entry._1, (entry._2, entry._3)))

  private def next(step: String, options: (String, String)): String = step match
    case "R" => options._2
    case _ => options._1

  private def getWasteLandLocation(step: String): (String, String) = wasteLand(step)

  def part1(): Int =

    def atEnd(step: String) = step == "ZZZ"

    @tailrec
    def traverse(path: String, step: String, count: Int = 0): Int =
      if atEnd(step) then count
      else if path.isEmpty then traverse(route, step, count)
      else
        val options = getWasteLandLocation(step)
        val nextStep = next(path.head.toString, options)
        traverse(path.tail, nextStep, count + 1)

    traverse(route, start)

  end part1

  def part2(): Int =

    def atEnd(steps: List[String]) = steps.count(_.endsWith("Z")) == steps.size

    def getStartLocations = wasteLand.keys.filter(_.endsWith("A")).toList

    @tailrec
    def traverse(path: String, steps: List[String], count: Int = 0): Int =
      if atEnd(steps) then count
      else if path.isEmpty then traverse(route, steps, count)
      else
        val options = steps.map(getWasteLandLocation)
        val nextSteps = options.map(item => next(path.head.toString, item))
        traverse(path.tail, nextSteps, count + 1)

    traverse(route, getStartLocations)

  end part2
