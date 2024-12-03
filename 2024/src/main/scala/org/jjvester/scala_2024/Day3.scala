package org.jjvester.scala_2024

import scala.annotation.tailrec

private[scala_2024] object Day3:
  private final val FIND_MULTIPLY_REGEX = "mul[(][0-9]{1,3},[0-9]{1,3}[)]".r
  private final val FIND_OPERANDS = "[0-9]{1,3}".r

  private def findMatches(src: String) = FIND_MULTIPLY_REGEX.findAllIn(src).toList
  private def calculateExpression(src: String): Long = FIND_OPERANDS.findAllIn(src).map(_.toLong).product

  @tailrec
  private def sumRow(row: List[String], acc: Long = 0): Long =
    if row.isEmpty then acc
    else sumRow(row.tail,  calculateExpression(row.head) + acc)

  @tailrec
  private def sumRows(input: List[String], acc: Long = 0): Long =
    if input.isEmpty then acc
    else sumRows(input.tail, sumRow(findMatches(input.head)) + acc)

  private def findAllValidCalculations(src: String): String = src.split("don't[(][)]").filter(_.contains("do()")).map(item => item.substring(item.indexOf("do()"))).mkString

  def part1(input: List[String]): Long = sumRows(input)

  def part2(input: List[String]): Long = sumRows(List(findAllValidCalculations("do()" + input.mkString)))
