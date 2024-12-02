package org.jjvester.scala_2024

import scala.annotation.tailrec

private[scala_2024] object Day2:

  private def toReport(src: String): List[Int] = src.split(" ").map(_.toInt).toList
  private def isDeltaInThreshold(max: Int, min: Int) = max - min <= 3 && max - min > 0

  @tailrec
  private def isDescendingSafeReport(report: List[Int], prev: Int): Boolean =
    if report.isEmpty then true
    else if !isDeltaInThreshold(prev, report.head) then false
    else isDescendingSafeReport(report.tail, report.head)

  @tailrec
  private def isAscendingSafeReport(report: List[Int], prev: Int): Boolean =
    if report.isEmpty then true
    else if !isDeltaInThreshold(report.head, prev) then false
    else isAscendingSafeReport(report.tail, report.head)

  private def isSafe(report: List[Int]): Boolean =
    if report.head > report.tail.head then isDescendingSafeReport(report.tail, report.head)
    else isAscendingSafeReport(report.tail, report.head)

  @tailrec
  private def countSafeReports(reports: List[String], count: Int = 0): Int =
    if reports.isEmpty then count
    else if isSafe(toReport(reports.head)) then countSafeReports(reports.tail, count + 1)
    else countSafeReports(reports.tail, count)

  def part1(input: List[String]): Int = countSafeReports(input)

  def part2(input: List[String]): Int = countSafeReports(input)