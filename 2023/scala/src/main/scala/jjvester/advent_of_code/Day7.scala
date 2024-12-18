package jjvester.advent_of_code

import scala.annotation.tailrec
import scala.collection.mutable

private[advent_of_code] class Day7:
  private val FIVE_OF_A_KIND = "FIVE_OF_A_KIND"
  private val FOUR_OF_A_KIND = "FOUR_OF_A_KIND"
  private val FULL_HOUSE = "FULL_HOUSE"
  private val THREE_OF_A_KIND = "THREE_OF_A_KIND"
  private val TWO_PAIR = "TWO_PAIR"
  private val ONE_PAIR = "ONE_PAIR"
  private val HIGH_CARD = "HIGH_CARD"
  private val RANK_ORDER = List(HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND)
  private val types = mutable.Map[String, List[Hand]](HIGH_CARD -> List.empty, ONE_PAIR -> List.empty, TWO_PAIR -> List.empty,
    THREE_OF_A_KIND -> List.empty, FULL_HOUSE -> List.empty, FOUR_OF_A_KIND -> List.empty, FIVE_OF_A_KIND -> List.empty)

  private class Hand(val cards: String, val bid: Int)

  private def isFiveOfAKind(hand: Hand) = hand.cards.distinct.length == 1

  private def isFourOfAKind(hand: Hand) =
    val groups = hand.cards.groupBy(card => card)
    groups.size == 2 && groups.values.map(entry => entry.length).toList.contains(4)

  private def isFullHouse(hand: Hand) =
    val groups = hand.cards.groupBy(card => card)
    //groups.size == 2 && groups.values.map(entry => entry.length).toList.contains(3)
    groups.size == 2

  private def isThreeOfAKind(hand: Hand) =
    val groups = hand.cards.groupBy(card => card)
    groups.size == 3 && groups.values.map(entry => entry.length).toList.contains(3)

  private def isTwoPair(hand: Hand) =
    val groups = hand.cards.groupBy(card => card)
    groups.size == 3 && groups.values.map(entry => entry.length).toList.count(elem => elem == 2) == 2

  private def isOnePair(hand: Hand) =
    val groups = hand.cards.groupBy(card => card)
    groups.size == 4 && groups.values.map(entry => entry.length).toList.contains(2)

  private def collectHand(entry: String): Hand =
    val parts = entry.split(" ")
    new Hand(parts.head.trim, parts.tail.head.trim.toInt)

  @tailrec
  private def collectHands(src: List[String], hands: List[Hand] = List.empty): List[Hand] =
    if src.isEmpty then hands
    else collectHands(src.tail, collectHand(src.head) :: hands)

  @tailrec
  private def groupByType(hands: List[Hand]): Unit =
    if hands.nonEmpty then
      if isFiveOfAKind(hands.head) then types.update(FIVE_OF_A_KIND, hands.head :: types(FIVE_OF_A_KIND))
      else if isFourOfAKind(hands.head)  then types.update(FOUR_OF_A_KIND, hands.head :: types(FOUR_OF_A_KIND))
      else if isFullHouse(hands.head) then types.update(FULL_HOUSE, hands.head :: types(FULL_HOUSE))
      else if isThreeOfAKind(hands.head) then types.update(THREE_OF_A_KIND, hands.head :: types(THREE_OF_A_KIND))
      else if isTwoPair(hands.head) then types.update(TWO_PAIR, hands.head :: types(TWO_PAIR))
      else if isOnePair(hands.head) then types.update(ONE_PAIR, hands.head :: types(ONE_PAIR))
      else types.update(HIGH_CARD, hands.head :: types(HIGH_CARD))
      groupByType(hands.tail)

  private def toSortableCards(cards: String) = cards.replaceAll("T", "a").replaceAll("J", "b").replaceAll("Q", "c").replaceAll("K", "d").replaceAll("A", "e")
  private def sortByHandStrength(handA: Hand, handB: Hand): Boolean = sortByCardStrength(toSortableCards(handA.cards), toSortableCards(handB.cards))

  @tailrec
  private def sortByCardStrength(cardsA: String, cardsB: String): Boolean =
    if cardsA.head < cardsB.head then true
    else if cardsA.head > cardsB.head then false
    else sortByCardStrength(cardsA.tail, cardsB.tail)

  private def pointsForRank(rankOrderKey: String, rank: Int): Int =
    val hands = types(rankOrderKey)
    if hands.size == 1 then
      println(hands.head.cards + " " + rank)
      hands.head.bid * rank
    else
      var currentRank = rank
      var total = 0
      val sortedHands = hands.sortWith(sortByHandStrength)
      for hand <- sortedHands do
        println(hand.cards + " " + currentRank)
        total += hand.bid * currentRank
        currentRank += 1
      total

  @tailrec
  private def tallyPointsByRank(rankOrder: List[String], rank: Int = 1, total: Int = 0): Int =
    if rankOrder.isEmpty then total
    else if types(rankOrder.head).isEmpty then tallyPointsByRank(rankOrder.tail, rank, total)
    else tallyPointsByRank(rankOrder.tail, rank + types(rankOrder.head).size, total + pointsForRank(rankOrder.head, rank))

  def part1(input: List[String]): Int =
    val hands = collectHands(input)
    groupByType(hands)
    tallyPointsByRank(RANK_ORDER)


