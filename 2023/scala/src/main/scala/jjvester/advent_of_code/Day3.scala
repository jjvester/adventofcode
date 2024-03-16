package jjvester.advent_of_code

import scala.annotation.tailrec

private[advent_of_code] class Day3(val x: Int, val y: Int):
  private val matrix = Matrix(x, y)

  private final class Cell(val idx: Int, val value: String, val indexes: List[Int])

  private def isGear(part: String) = part == "*"
  private def isPeriod(char: Char) = char == '.'
  private def isSymbol(char: Char) = !char.isDigit
  private def isPartSymbol(char: Char) = isSymbol(char) && !isPeriod(char)
  private def isDigit(char: Char) = char.isDigit
  private def getNeighbors(cell: Cell): List[Int] = matrix.adjacent(cell.idx)
  private def getAllNeighbors(symbols: Map[Int, Cell]): List[Int] = symbols.values.flatMap(getNeighbors).toList

  @tailrec
  private def collectSymbols(rows: List[String], index: Int = 0, symbols: Map[Int, Cell] = Map.empty): Map[Int, Cell] =
    if rows.isEmpty then symbols
    else collectSymbols(rows.tail, index + x, updatedSymbols(rows.head, index, symbols))

  @tailrec
  private def collectNumbers(rows: List[String], index: Int = 0, numbers: Map[Int, Cell] = Map.empty): Map[Int, Cell] =
    if rows.isEmpty then numbers
    else collectNumbers(rows.tail, index + x, updatedPartNumbers(rows.head, index, "", List.empty, numbers))

  @tailrec
  private def updatedSymbols(row: String, index: Int, symbols: Map[Int, Cell]): Map[Int, Cell] =
    if row.isEmpty then symbols
    else if isPartSymbol(row.head) then updatedSymbols(row.tail, index + 1, symbols.updated(index, new Cell(index, row.head.toString, List(index))))
    else updatedSymbols(row.tail, index + 1, symbols)

  @tailrec
  private def updatedPartNumbers(row: String,
                                 index: Int,
                                 accNumber: String,
                                 accIndexes: List[Int],
                                 numbers: Map[Int, Cell]): Map[Int, Cell] =

    @tailrec
    def updateNumbers(indexes: List[Int], partNumber: String, numbersAccumulator: Map[Int, Cell]): Map[Int, Cell] =
      if indexes.isEmpty then numbersAccumulator
      else updateNumbers(indexes.tail, partNumber, numbersAccumulator.updated(indexes.head, new Cell(indexes.head, partNumber, accIndexes)))
    end updateNumbers

    if row.isEmpty && accNumber.nonEmpty then updateNumbers(accIndexes, accNumber, numbers)
    else if row.isEmpty then numbers
    else if isDigit(row.head) then updatedPartNumbers(row.tail, index + 1, accNumber.appended(row.head), index :: accIndexes, numbers)
    else if accNumber.nonEmpty then updatedPartNumbers(row.tail, index + 1, "", List.empty, updateNumbers(accIndexes, accNumber, numbers))
    else updatedPartNumbers(row.tail, index + 1, accNumber, accIndexes, numbers)
  end updatedPartNumbers

  @tailrec
  private def draw(src: List[String], index: Int = 0): Unit =
    if src.nonEmpty then
      println(src.head + ":" + index)
      draw(src.tail, index + x)

  @tailrec
  private def collectEngineParts(neighbors: List[Int], partNumbers: Map[Int, Cell], symbols: Map[Int, Cell], usedIndexes: Set[Int] = Set.empty, acc: Int = 0): Int =
    if neighbors.isEmpty then acc
    else if !usedIndexes.contains(neighbors.head) then
      val currentIndex = neighbors.head
      val potentialPartNumber = partNumbers.find(partNumberMapping => partNumberMapping._1 == currentIndex)
      if potentialPartNumber.isDefined then
        println("Collected engine part " + potentialPartNumber.get._2.value.toInt + " reserving indexes " + potentialPartNumber.get._2.indexes)
        collectEngineParts(neighbors.tail, partNumbers, symbols, usedIndexes ++ potentialPartNumber.get._2.indexes, potentialPartNumber.get._2.value.toInt + acc)
      else collectEngineParts(neighbors.tail, partNumbers, symbols, usedIndexes, acc)
    else collectEngineParts(neighbors.tail, partNumbers, symbols, usedIndexes, acc)

  def part1(input: List[String]): Int =
    draw(input)
    val partNumbers = collectNumbers(input)
    val symbols = collectSymbols(input)
    val neighbors = getAllNeighbors(symbols).distinct

    collectEngineParts(neighbors, partNumbers, symbols)

  def part2(input: List[String]): Int =
    draw(input)
    val gears = collectSymbols(input).filter(symbolPair => isGear(symbolPair._2.value))
    val partNumbers = collectNumbers(input)
    val usedIndexes: collection.mutable.Set[Int] = collection.mutable.Set.empty
    var sum = 0
    for (_, v) <- gears do
      val neighbors = getNeighbors(v).filter(neighbor => partNumbers.contains(neighbor))
      val gearParts = neighbors.map(neighbor => partNumbers(neighbor).value.toInt).distinct
      if gearParts.size == 2 && !usedIndexes.contains(neighbors.head) && !usedIndexes.contains(neighbors(1)) then
          usedIndexes.addAll(neighbors)
          sum += gearParts.head * gearParts(1)
    sum
