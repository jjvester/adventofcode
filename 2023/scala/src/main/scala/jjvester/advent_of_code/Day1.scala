package jjvester.advent_of_code

import scala.annotation.tailrec

private[advent_of_code] object Day1:
  private val EMPTY_STRING: String = ""

  private def getFirstDigitWithIndex(src: String): Option[(String, Int)] = src.find(_.isDigit).map(elem => (elem.toString, src.indexOf(elem)))
  private def getFirstDigit(src: String): Option[String] = getFirstDigitWithIndex(src).map(pair => pair._1)
  private def getFirstAlphaNumberWithIndex(src: String, matchSet: Map[String, Int]): Option[(String, Int)] = findFirst(src, matchSet)

  private def findFirstNumber(src: String, alphabeticNumberMappings: Map[String, Int]): Option[String] =
    val firstAlphaNumberWithIndex = getFirstAlphaNumberWithIndex(src, alphabeticNumberMappings)
    val firstDigitWithIndex = getFirstDigitWithIndex(src)

    def getSmallestIndexNumber(alphabeticNumberWithIndex: (String, Int), digitWithIndex: (String, Int)): Option[String] =
      if alphabeticNumberWithIndex._2 < digitWithIndex._2 then Option(alphabeticNumberMappings(alphabeticNumberWithIndex._1).toString)
      else Option(digitWithIndex._1)

    if firstAlphaNumberWithIndex.isDefined && firstDigitWithIndex.isDefined then getSmallestIndexNumber(firstAlphaNumberWithIndex.get, firstDigitWithIndex.get)
    else if firstAlphaNumberWithIndex.isDefined then firstAlphaNumberWithIndex.map(pair => alphabeticNumberMappings(pair._1).toString)
    else if firstDigitWithIndex.isDefined then firstDigitWithIndex.map(pair => pair._1)
    else Option.empty
  end findFirstNumber

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

  private[advent_of_code] def part1(input:List[String]): Int = recur(input, (a: String) => (getFirstDigit(a).getOrElse(EMPTY_STRING) + "" + getFirstDigit(a.reverse).getOrElse(EMPTY_STRING)).toInt)

  private[advent_of_code] def part2(input: List[String]): Int =
    val alphabeticNumberMappings = Map("one" -> 1, "two" -> 2, "three" -> 3, "four" -> 4, "five" -> 5, "six" -> 6, "seven" -> 7, "eight" -> 8, "nine" -> 9, "ten" -> 10)
    recur(input, (a: String) => {
    val first = findFirstNumber(a, alphabeticNumberMappings).getOrElse(EMPTY_STRING)
    val last = findFirstNumber(a.reverse, alphabeticNumberMappings.map(pair => pair._1.reverse -> pair._2)).getOrElse(EMPTY_STRING)
    (first + "" + last).toInt
  })