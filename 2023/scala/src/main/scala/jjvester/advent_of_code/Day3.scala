package jjvester.advent_of_code

import scala.annotation.tailrec

private[advent_of_code] class Day3(val x: Int, val y: Int):
  private val matrix = Matrix(x, y)

  private final class Cell(val idx: Int, val value: String)

  private def isPeriod(char: Char) = char == '.'
  private def isSymbol(char: Char) = !char.isDigit
  private def isDigit(char: Char) = char.isDigit

  @tailrec
  private def filterRowBy(row: String, filter: Char => Boolean, idx: Int, acc: Map[Int, Cell]): Map[Int, Cell] =
    if row.isEmpty then acc
    else if filter(row.head) then filterRowBy(row.tail, filter, idx + 1, acc.updated(idx, new Cell(idx, row.head.toString)))
    else filterRowBy(row.tail, filter, idx + 1, acc)

  @tailrec
  private def forAllRows(rows: List[String], rowIdx: Int = 0, nonPeriodSymbols: Map[Int, Cell] = Map.empty, digits: Map[Int, Cell] = Map.empty): (Map[Int, Cell], Map[Int, Cell]) =
    if rows.isEmpty then (nonPeriodSymbols, digits)
    else forAllRows(rows.tail, rowIdx + rows.head.length,
                    filterRowBy(rows.head, char => isSymbol(char) && !isPeriod(char), rowIdx, nonPeriodSymbols),
                    filterRowBy(rows.head, char => isDigit(char), rowIdx, digits))

  private def getNeighbors(cell: Cell): List[Int] = matrix.adjacent(cell.idx)

  @tailrec
  private def draw(src: List[String]): Unit =
    if src.nonEmpty then
      println(src.head)
      draw(src.tail)

  def part1(input: List[String]): Int =
    draw(input)
    val symbols, digits = forAllRows(input)
   // val symbols = getSymbols(input)
    0