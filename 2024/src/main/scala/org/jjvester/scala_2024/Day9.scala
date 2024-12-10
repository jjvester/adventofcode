package org.jjvester.scala_2024

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

private[scala_2024] class Day9:

  private var revStack: mutable.Stack[String] = mutable.Stack.empty
  private var rawBuffer: mutable.ListBuffer[String] = mutable.ListBuffer.empty
  private var popped: List[String] = List.empty

  private enum FileType:
    case FILE
    case EMPTY

  private def addBlocks(size: Int, fileType: FileType, id: Int): String = fileType match
      case FileType.FILE => (0 until size).map(_ => id).mkString
      case _ => (0 until size).map(_ => ".").mkString

  private def flip(fileType: FileType): FileType = fileType match
    case FileType.FILE => FileType.EMPTY
    case _ => FileType.FILE

  @tailrec
  private def spread(input: String, result: String, fileType: FileType, index: Int = 0): String =
    if input.isEmpty then result
    else if fileType == FileType.EMPTY then spread(input.tail, result + addBlocks(input.head.toString.toInt, fileType, index), flip(fileType), index)
    else spread(input.tail, result + addBlocks(input.head.toString.toInt, fileType, index), flip(fileType), index + 1)

  @tailrec
  private def condense(rawIndex: Int, revIndex: Int): Int =
    if rawIndex >= revIndex then rawIndex
    else if rawBuffer(rawIndex) == "." then
      val replacement = revStack.pop()
      popped = replacement :: popped
      if replacement == "." then condense(rawIndex, revIndex - 1)
      else
        rawBuffer(rawIndex) = replacement
        condense(rawIndex + 1, revIndex - 1)
    else condense(rawIndex + 1, revIndex)


  def part1(input: List[String]): Int =
    val data = input.mkString
    val result = spread(data, "", FileType.FILE)

    result.foreach(item => rawBuffer.addOne(item.toString))
    result.foreach(item => revStack.push(item.toString))

//    println(rawBuffer)
//    println(revStack)

    val stoppedIndex = condense(0, revStack.size - 1)
    val checkSum = rawBuffer.mkString.substring(0, result.length - popped.size)

    println(checkSum)
    checkSum.zipWithIndex.map((item, index) => item.toString.toInt * index).sum
