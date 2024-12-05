package org.jjvester.scala_2024

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

private[scala_2024] class Day5:
  private var rules = Map[String, Set[String]]()

  private def addRule(rule: String): Unit =
    val keyAndValue = rule.split("[|]")
    rules = rules.updatedWith(keyAndValue.head)(potentialValue => Option(potentialValue.getOrElse(Set.empty) + keyAndValue.tail.head))

  @tailrec
  private def extractRules(input: List[String]): List[String] =
    if input.head.isEmpty then input.tail
    else
      addRule(input.head)
      extractRules(input.tail)

  private def isPageValid(current: String, valid: List[String]): Boolean =
    val next = rules.get(current)
    if next.isEmpty then true
    else next.exists(nextPages => nextPages.forall(!valid.contains(_)))

  @tailrec
  private def forAllPages(pages: List[String], valid: List[String] = Nil): List[String] =
    if pages.isEmpty then valid
    else if !isPageValid(pages.head, valid) then Nil
    else forAllPages(pages.tail, pages.head :: valid)

  def part1(input: List[String]): Int =
    val pages = extractRules(input)
    val validPages = pages.map( { chapter =>
      val valid = forAllPages(chapter.split(",").toList)
      valid
    }).filter(_.nonEmpty)

    val middlePageOfEach = validPages.map(item => item(item.size / 2))
    middlePageOfEach.map(_.toInt).sum

  @tailrec
  private def findInvalidPage(chapter: ListBuffer[String], valid: List[String] = Nil, index: Int = 0): (String, List[String], Int) =
    if chapter.isEmpty then ("", valid, index)
    else if isPageValid(chapter.head, valid) then findInvalidPage(chapter.tail, chapter.head :: valid, index + 1)
    else (chapter.head, valid, index)

  def part2(input: List[String]): Int =
    val pages = extractRules(input)
    val invalidPages = pages.map( { chapter =>
      val invalid = if forAllPages(chapter.split(",").toList).isEmpty then chapter.split(",").toList else List.empty
      invalid
    }).filter(_.nonEmpty)


    var results: List[ListBuffer[String]] = List.empty
    var buffer: ListBuffer[String] = ListBuffer.empty
    invalidPages.foreach({ chapter =>

      buffer = ListBuffer.empty ++= chapter
      var invalidPageResult = findInvalidPage(buffer)
      while(invalidPageResult._1.nonEmpty) do

        val validPrefix = invalidPageResult._2.reverse
        buffer(invalidPageResult._3) = validPrefix.last
        buffer(validPrefix.size - 1) = invalidPageResult._1

        println("Original " + chapter)
        println("Invalid item " + invalidPageResult._1)
        println("Valid prefix " + validPrefix)
        println("Invalid index " + invalidPageResult._3)
        println("Updated after swap " + buffer)
        println("*****")

        invalidPageResult = findInvalidPage(buffer)
      end while
      results = buffer :: results
    })
    println(results)
    val middlePageOfEach = results.map(item => item(item.size / 2))
    middlePageOfEach.map(_.toInt).sum