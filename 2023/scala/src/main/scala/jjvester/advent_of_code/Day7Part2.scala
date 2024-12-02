package jjvester.advent_of_code

import scala.annotation.tailrec
import scala.collection.mutable

private[advent_of_code] class Day7Part2 :
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

  private def groupHand(hand: Hand): Map[Char, String] = hand.cards.groupBy(card => card)
  private def groupHandWithNoJoker(hand: Hand) = hand.cards.filter(elem => elem != 'J').groupBy(card => card)
  private def isFiveOfAKind(groups: Map[Char, String]) = groups.size == 1
  private def isFourOfAKind(groups: Map[Char, String]) = groups.size == 2 && groups.values.map(entry => entry.length).toList.contains(4)
  private def isFullHouse(groups: Map[Char, String]) = groups.size == 2 && groups.values.count(cards => cards.length > 1) == 2
  private def isThreeOfAKind(groups: Map[Char, String]) = groups.size == 3 && groups.values.map(entry => entry.length).toList.contains(3)
  private def isTwoPair(groups: Map[Char, String]) = groups.size == 3 && groups.values.map(entry => entry.length).toList.count(elem => elem == 2) == 2
  private def isOnePair(groups: Map[Char, String]) = groups.size == 4 && groups.values.map(entry => entry.length).toList.contains(2)

  private def collectHand(entry: String): Hand =
    val parts = entry.split(" ")
    new Hand(parts.head.trim, parts.tail.head.trim.toInt)

  @tailrec
  private def collectHands(src: List[String], hands: List[Hand] = List.empty): List[Hand] =
    if src.isEmpty then hands
    else collectHands(src.tail, collectHand(src.head) :: hands)

  private def tryPromoteHandType(hand: Hand, typeIndex: Int): Int =
    val jokerCount = hand.cards.count(card => card == 'J')
    typeIndex + jokerCount

  private def containsJoker(groups: Map[Char, String]) = groups.contains('J')

  @tailrec
  private def groupByType(hands: List[Hand]): Unit =
    if hands.nonEmpty then
      val groupsNoJokers = groupHandWithNoJoker(hands.head)
      val groupsWithJokers = groupHand(hands.head)

      if isFiveOfAKind(groupsNoJokers) || isFiveOfAKind(groupsWithJokers) then
        println(hands.head.cards + " FIVE_OF_A_KIND")
        types.update(FIVE_OF_A_KIND, hands.head :: types(FIVE_OF_A_KIND))

      else if isFourOfAKind(groupsWithJokers) && containsJoker(groupsWithJokers) then
        println(hands.head.cards + " promoted to FIVE_OF_A_KIND")
        types.update(FIVE_OF_A_KIND, hands.head :: types(FIVE_OF_A_KIND))
      else if isFourOfAKind(groupsWithJokers)  then
        println(hands.head.cards + " FOUR_OF_A_KIND")
        types.update(FOUR_OF_A_KIND, hands.head :: types(FOUR_OF_A_KIND))

      else if isFullHouse(groupsWithJokers) && containsJoker(groupsWithJokers) then
        println(hands.head.cards + " promoted to FIVE_OF_A_KIND")
        types.update(FIVE_OF_A_KIND, hands.head :: types(FIVE_OF_A_KIND))
      else if isFullHouse(groupsNoJokers) then
        println(hands.head.cards + " FULL_HOUSE")
        types.update(FULL_HOUSE, hands.head :: types(FULL_HOUSE))

      else if isThreeOfAKind(groupsWithJokers)  && containsJoker(groupsWithJokers) then
        println(hands.head.cards + " promoted to FOUR_OF_A_KIND")
        types.update(FOUR_OF_A_KIND, hands.head :: types(FOUR_OF_A_KIND))
      else if isThreeOfAKind(groupsNoJokers) then
        println(hands.head.cards + " THREE_OF_A_KIND")
        types.update(THREE_OF_A_KIND, hands.head :: types(THREE_OF_A_KIND))

      else if isTwoPair(groupsWithJokers) && containsJoker(groupsWithJokers) then
        val jokerCount = groupsWithJokers('J').length
        if jokerCount > 1 then
          println(hands.head.cards + " promoted to FOUR_OF_A_KIND")
          types.update(FOUR_OF_A_KIND, hands.head :: types(FOUR_OF_A_KIND))
        else
          println(hands.head.cards + " promoted to FULL_HOUSE")
          types.update(FULL_HOUSE, hands.head :: types(FULL_HOUSE))
      else if isTwoPair(groupsNoJokers) then
        println(hands.head.cards + " TWO_PAIR")
        types.update(TWO_PAIR, hands.head :: types(TWO_PAIR))

      else if isOnePair(groupsWithJokers) && containsJoker(groupsWithJokers) then
        println(hands.head.cards + " promoted to THREE_OF_A_KIND")
        types.update(THREE_OF_A_KIND, hands.head :: types(THREE_OF_A_KIND))
      else if isOnePair(groupsNoJokers) then
        println(hands.head.cards + " ONE_PAIR")
        types.update(ONE_PAIR, hands.head :: types(ONE_PAIR))
      else if containsJoker(groupsWithJokers) then
        println(hands.head.cards + " promoted to ONE_PAIR")
        types.update(ONE_PAIR, hands.head :: types(ONE_PAIR))
      else
        println(hands.head.cards + " HIGH_CARD")
        types.update(HIGH_CARD, hands.head :: types(HIGH_CARD))
      groupByType(hands.tail)

  private def toSortableCards(cards: String) = cards.replaceAll("T", "a").replaceAll("J", "1").replaceAll("Q", "c").replaceAll("K", "d").replaceAll("A", "e")
  private def sortByHandStrength(handA: Hand, handB: Hand): Boolean = sortByCardStrength(toSortableCards(handA.cards), toSortableCards(handB.cards))

  @tailrec
  private def sortByCardStrength(cardsA: String, cardsB: String): Boolean =
    if cardsA.head < cardsB.head then true
    else if cardsA.head > cardsB.head then false
    else sortByCardStrength(cardsA.tail, cardsB.tail)

  private def pointsForRank(rankOrderKey: String, rank: Int): Int =
    val hands = types(rankOrderKey)
    if hands.size == 1 then
      //println(hands.head.cards + " " + rank)
      hands.head.bid * rank
    else
      var currentRank = rank
      var total = 0
      val sortedHands = hands.sortWith(sortByHandStrength)
      for hand <- sortedHands do
        //println(hand.cards + " " + currentRank)
        total += hand.bid * currentRank
        currentRank += 1
      total

  @tailrec
  private def tallyPointsByRank(rankOrder: List[String], rank: Int = 1, total: Int = 0): Int =
    if rankOrder.isEmpty then total
    else if types(rankOrder.head).isEmpty then tallyPointsByRank(rankOrder.tail, rank, total)
    else tallyPointsByRank(rankOrder.tail, rank + types(rankOrder.head).size, total + pointsForRank(rankOrder.head, rank))

  def part2(input: List[String]): Int =
    val hands = collectHands(input)
    groupByType(hands)
    tallyPointsByRank(RANK_ORDER)



