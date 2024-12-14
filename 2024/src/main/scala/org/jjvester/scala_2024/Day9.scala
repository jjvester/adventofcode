package org.jjvester.scala_2024

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.boundary
import scala.util.boundary.break

private[scala_2024] class Day9:

  private val revStack: mutable.Stack[String] = mutable.Stack.empty
  private var buffer: mutable.ListBuffer[String] = mutable.ListBuffer.empty

  private enum FileType:
    case FILE
    case EMPTY

  private def addBlocks(size: Int, fileType: FileType, id: Int): List[String] = fileType match
    case FileType.FILE => (0 until size).map(_ => id.toString).toList
    case _ => (0 until size).map(_ => ".").toList

  private def flip(fileType: FileType): FileType = fileType match
    case FileType.FILE => FileType.EMPTY
    case _ => FileType.FILE

  @tailrec
  private def spread(input: String, result: List[String], fileType: FileType, index: Int = 0): ListBuffer[String] =
    if input.isEmpty then ListBuffer.empty ++= result
    else if fileType == FileType.EMPTY then spread(input.tail, result.appendedAll(addBlocks(input.head.toString.toInt, fileType, index)), flip(fileType), index)
    else spread(input.tail, result.appendedAll(addBlocks(input.head.toString.toInt, fileType, index)), flip(fileType), index + 1)

  @tailrec
  private def condense(index: Int, revIndex: Int, count: Int = 0): Int =
    if index >= revIndex then count
    else if buffer(index) == "." then
      val replacement = revStack.pop()
      if replacement == "." then condense(index, revIndex - 1, count + 1)
      else
        buffer(index) = replacement
        condense(index + 1, revIndex - 1, count + 1)
    else condense(index + 1, revIndex, count)

  private def init(input: List[String]): Unit =
    val data = input.mkString

    buffer = spread(data, List.empty, FileType.FILE)
    buffer.foreach(item => revStack.push(item))

  def part1(input: List[String]): Long =
    init(input)

    val removed = condense(0, revStack.size)
    val netEffect = buffer.take(buffer.size - removed)
    val checkSum = netEffect.zipWithIndex.map((item, index) => item.toLong * index).sum
    checkSum

  def part2(input: List[String]): Long =
    init(input)

    var resultBuffer: ListBuffer[String] = ListBuffer.empty

    def findSpace(candidate: String, candidateSize: Int, revIndex: Int): Boolean =
      var potentialSize = 0
      var result = false
      boundary:
        buffer.zipWithIndex.foreach((data, index) => {
          if index < revIndex then
            if potentialSize == candidateSize then
              //println(s"Found space for $candidateSize")
              (index - candidateSize until index).foreach(buffer(_) = candidate)
              result = true
              break()
            else if data == "." then
              potentialSize += 1
            else potentialSize = 0
        })
      result

    var revIndex = revStack.size
    var candidate = ""
    var candidateSize = 0
    revStack.foreach(data => {
        if data != "." then
          if candidateSize == 0 then
            candidateSize += 1
            candidate = data
          else if data != candidate then
            //println(s"Going to find space for $candidateSize")
            if findSpace(candidate, candidateSize, revIndex) then
              //println("Fix old space with periods")
              (revIndex until revIndex + candidateSize).foreach(buffer(_) = ".")
            candidateSize = 1
            candidate = data
          else if data == candidate then
            candidate = data
            candidateSize += 1
          else
            //println(s"Going to find space for $candidateSize")
            if findSpace(candidate, candidateSize, revIndex) then
             // println("Fix old space with periods")
              (revIndex until revIndex + candidateSize).foreach(buffer(_) = ".")
            candidateSize = 0
            candidate = ""
        else if candidateSize > 0 then
          //println(s"Going to find space for $candidateSize")
          if findSpace(candidate, candidateSize, revIndex) then
            //println("Fix old space with periods")
            (revIndex until revIndex + candidateSize).foreach(buffer(_) = ".")
          candidateSize = 0
          candidate = ""
        else
          candidateSize = 0
          candidate = ""

        revIndex -= 1
    })
    val result = buffer.zipWithIndex.map((item, index) => if item == "." then 0L else item.toLong * index).sum
    println(result)
    result