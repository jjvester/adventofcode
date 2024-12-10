package org.jjvester.scala_2024

private[scala_2024] object Day7:

  private def toEquation(input: String): (Long, List[Long]) =
    val raw = input.split(":")
    val answer = raw.head.toLong
    val operands = raw.tail.head.trim().split(" ").map(_.toLong).toList
    (answer, operands)

  private def prove(answer: Long, operands: List[Long], total: Long = 0): Boolean =
    if operands.isEmpty then total == answer
    else if total > answer then false
    else prove(answer, operands.tail, if total == 0 then 1 * operands.head else operands.head * total) || prove(answer, operands.tail, operands.head + total)

  private def proveByProductOrSum(answer: Long, operands: List[Long]): Boolean =
    if answer == operands.product then true
    else if answer == operands.sum then true
    else false

  private def sumCalibration(src: List[(Long, List[Long])], predicate: (Long, List[Long]) => Boolean): Long = src.filter(item => predicate(item._1, item._2) || prove(item._1, item._2)).map(_._1).sum

  def part1(input: List[String]): Long =
    val sanitized = input.map(toEquation)
    sumCalibration(sanitized, proveByProductOrSum)

  def part2(input: List[String]): Long =
    def proveByProductOrSumOrConcatenation(answer: Long, operands: List[Long]): Boolean =
      if proveByProductOrSum(answer, operands) then true
      else answer == operands.map(_.toString).mkString.toLong
    end proveByProductOrSumOrConcatenation

    def prove(answer: Long, operands: List[Long], total: Long = 0): Boolean =
      if operands.isEmpty then total == answer
      else if total > answer then false
      else prove(answer, operands.tail, if total == 0 then 1 * operands.head else operands.head * total)
        || prove(answer, operands.tail, operands.head + total) || prove(answer, operands.tail, (total + "" + operands.head).toLong)
    end prove

    val sanitized = input.map(toEquation)
    sanitized.filter(item => proveByProductOrSumOrConcatenation(item._1, item._2) || prove(item._1, item._2)).map(_._1).sum