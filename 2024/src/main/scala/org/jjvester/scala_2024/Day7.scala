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

  def part1(input: List[String]): Long =
    val sanitized = input.map(toEquation)
//    println(sanitized)
//
//    sanitized.foreach(item => {
//      if prove(item._1, item._2) then
//        println(item._1)
//    })

    sanitized.filter(item => prove(item._1, item._2)).map(_._1).sum
