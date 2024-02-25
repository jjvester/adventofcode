package jjvester.advent_of_code

import scala.annotation.tailrec

private[advent_of_code] object Day4:
  private def getNumbers(src: String) = src.split(":").tail.head
  private def getWinningNumbers(src: String) = src.split("[|]").head.split(" ").toList.filter(_.nonEmpty)
  private def getPlayedNumbers(src: String) = src.split("[|]").tail.head.split(" ").toList.filter(_.nonEmpty)

  @tailrec
  def part1(input: List[String], points: Int = 0): Int =
    if input.isEmpty then points
    else
      val numbers = getNumbers(input.head)
      val winningNumbers = getWinningNumbers(numbers)
      val playedNumbers = getPlayedNumbers(numbers)
      val matches = playedNumbers.count(item => winningNumbers.contains(item))
      part1(input.tail, points + (if matches <= 1 then matches else Math.pow(2, matches - 1).toInt))

  @tailrec
  private def forEachCopy(card: Int, count: Int, wins: Int, copies: Map[Int, Int]): Map[Int, Int] =
    if count == 0 then copies
    else forEachCopy(card, count - 1, wins, updateCopies(card, wins, copies))

  @tailrec
  private def updateCopies(card: Int, wins: Int, copies: Map[Int, Int]): Map[Int, Int] =
    if wins == 0 then copies
    else updateCopies(card + 1, wins - 1, copies.updated(card, copies.getOrElse(card, 0) + 1))

  @tailrec
  def part2(input: List[String], idx: Int = 1, copies: Map[Int, Int] = Map.empty): Int =
    if input.isEmpty then copies.values.sum
    else
      val numbers = getNumbers(input.head)
      val winningNumbers = getWinningNumbers(numbers)
      val playedNumbers = getPlayedNumbers(numbers)
      val matches = playedNumbers.count(item => winningNumbers.contains(item))
      part2(input.tail, idx + 1, forEachCopy(idx + 1, copies.getOrElse(idx, 0) + 1, matches, copies.updated(idx, copies.getOrElse(idx, 0) + 1)))