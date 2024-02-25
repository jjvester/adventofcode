package jjvester.advent_of_code

import scala.annotation.tailrec

private[advent_of_code] object Day1:
  private def getFirstDigitWithIndex(src: String): Option[(String, Int)] = src.find(_.isDigit).map(elem => (elem.toString, src.indexOf(elem)))
  private def getFirstDigit(src: String): Option[String] = getFirstDigitWithIndex(src).map(pair => pair._1)

  private def getFirstAlphaNumberWithIndex(src: String, matchSet: Map[String, Int]): Option[(String, Int)] = {
    val result = findFirst(src, matchSet)
      //result.map(pair => (pair._1, matchSet(pair._1)))
      result
  }
  private def getFirst(src: String, numbers: Map[String, Int]): Option[String] =
    val firstAlphaNumber = getFirstAlphaNumberWithIndex(src, numbers)
    val firstDigit = getFirstDigitWithIndex(src)
    if firstAlphaNumber.isDefined && firstDigit.isDefined then
      if firstAlphaNumber.get._2 < firstDigit.get._2 then firstAlphaNumber.map(pair => numbers(pair._1).toString) else firstDigit.map(pair => pair._1)
    else if firstAlphaNumber.isDefined then firstAlphaNumber.map(pair => numbers(pair._1).toString)
    else if firstDigit.isDefined then firstDigit.map(pair => pair._1)
    else Option.empty

  private val numbers = Map("one" -> 1, "two" -> 2, "three" -> 3, "four" -> 4, "five" -> 5, "six" -> 6, "seven" -> 7, "eight" -> 8, "nine" -> 9, "ten" -> 10)

  @tailrec
  private def findFirst(input: String, matchSet: Map[String, Int], matched: Option[(String, Int)] = Option.empty): Option[(String, Int)] =
    if matchSet.isEmpty then matched
    else if input.contains(matchSet.head._1) then findFirst(input, matchSet.tail, getClosest((matchSet.head._1, input.indexOf(matchSet.head._1)), matched))
    else findFirst(input, matchSet.tail, matched)

  private def getClosest(potentialCandidate: (String, Int), currentCandidate: Option[(String, Int)] = Option.empty): Option[(String, Int)] =
    if currentCandidate.isEmpty then Option((potentialCandidate._1, potentialCandidate._2))
    else if potentialCandidate._2 < currentCandidate.get._2 then Option((potentialCandidate._1, potentialCandidate._2))
    else currentCandidate

  @tailrec
  private def recur(src: List[String], calibration: String => Int, acc: Int = 0): Int =
    if src.isEmpty then acc
    else recur(src.tail, calibration, calibration(src.head) + acc)

  private[advent_of_code] def part1(input:List[String]): Int = recur(input, (a: String) => (getFirstDigit(a).getOrElse(Char.MinValue.toString) + "" + getFirstDigit(a.reverse).getOrElse(Char.MinValue.toString)).toInt)

  private[advent_of_code] def part2(input: List[String]): Int = recur(input, (a: String) => {
    val first = getFirst(a, numbers).getOrElse(Char.MinValue.toString)
    val last = getFirst(a.reverse, numbers.map(pair => pair._1.reverse -> pair._2)).getOrElse(Char.MinValue.toString)
    (first + "" + last).toInt
  })