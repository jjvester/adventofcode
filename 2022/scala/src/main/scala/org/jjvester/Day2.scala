package org.jjvester

import scala.annotation.tailrec

object Day2:
  private val hands: Map[String, Int] = Map("X" -> 1, "Y" -> 2, "Z" -> 3)
  private val wins: Map[String, String] = Map("A" -> "Y", "B" -> "Z", "C" -> "X")
  private val draws: Map[String, String] = Map("A" -> "X", "B" -> "Y", "C" -> "Z")
  private val losses: Map[String, String] = Map("A" -> "Z", "B" -> "X", "C" -> "Y")
  private val expectations: Map[String, Map[String, String]] = Map("X" -> losses, "Y" -> draws, "Z" -> wins)

  private def points(opponent: String, me: String): Int =
    val hand = hands(me)
    val bonus = if (wins(opponent).contains(me)) 6
    else if (draws(opponent).contains(me)) 3
    else 0

    hand + bonus

  def part1(input: List[String]): Int =
    @tailrec
    def recur(src: List[String], acc: Int): Int =
      if src.isEmpty then return acc

      val item = src.head
      val opponent = String.valueOf(item.charAt(0))
      val me = String.valueOf(item.charAt(2))
      recur(src.tail, acc + points(opponent, me))

    recur(input, 0)

  def part2(input: List[String]): Int =
    @tailrec
    def recur(src: List[String], acc: Int): Int =
      if src.isEmpty then return acc

      val item = src.head
      val opponent = String.valueOf(item.charAt(0))
      val expectation = String.valueOf(item.charAt(2))
      val me = expectations(expectation)(opponent)
      recur(src.tail, acc + points(opponent, me))


    recur(input, 0)