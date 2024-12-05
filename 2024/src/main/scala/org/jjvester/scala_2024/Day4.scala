package org.jjvester.scala_2024

import org.jjvester.scala_2024.Neighbor.{NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST}

import scala.annotation.tailrec

private[scala_2024] object Day4:

  private var keyWord = "XMAS"
  private var startToken = "X"

  @tailrec
  private def fillRow(row: String, grid: Grid[String], roots: Set[Int], index: Int): (Grid[String], Set[Int], Int) =
    if row.isEmpty then (grid, roots, index)
    else
      val token = row.head.toString
      grid.insert(token, index)
      if token == startToken then
        fillRow(row.tail, grid, roots + index, index + 1)
      else fillRow(row.tail, grid, roots, index + 1)

  @tailrec
  private def fillGrid(input: List[String], grid: Grid[String], roots: Set[Int] = Set.empty, index: Int = 0): (Grid[String], Set[Int]) =
    if input.isEmpty then (grid, roots)
    else
      val (updatedGrid, updatedRoots, updatedIndex) = fillRow(input.head, grid, roots, index)
      fillGrid(input.tail, updatedGrid, updatedRoots, updatedIndex)

  @tailrec
  private def searchDirection(current: Cell[String], direction: Neighbor, wordPart: String, grid: Grid[String], depth: Int, acc: Set[Int]): (Boolean, Set[Int]) =
    if keyWord == (wordPart + current.item) then (true, acc + current.index)
    else if wordPart.length >= keyWord.length then (false, Set.empty)
    else if !current.neighbors.contains(direction) then (false, Set.empty)
    else if wordPart.nonEmpty && !keyWord.startsWith(wordPart) then (false, Set.empty)
    else searchDirection(grid.getCell(current.neighbors(direction)).get, direction, wordPart + current.item, grid, depth + 1, acc + current.index)

  def part1(input: List[String]): Int =
    def searchGrid(start: Int, grid: Grid[String]): Int =
      val startCell = grid.getCell(start).get
      startCell.neighbors.keys.count(direction => searchDirection(startCell, direction, "", grid, 0, Set.empty)._1)
    end searchGrid

    val (grid, roots) = fillGrid(input, new Grid[String](input.head.length, input.size))
    var count = 0
    roots.foreach(root => {
      count += searchGrid(root, grid)
    })
    count
  end part1

  private def onlyDiagonal(neighbor: Neighbor) = neighbor match
    case NORTH_WEST => true
    case NORTH_EAST => true
    case SOUTH_WEST => true
    case SOUTH_EAST => true
    case _ => false

  def part2(input: List[String]): Int =
    keyWord = "MAS"
    startToken = "M"
    var matchedIndexes: List[Int] = List.empty

    @tailrec
    def searchDirection(current: Cell[String], direction: Neighbor, wordPart: String, grid: Grid[String], depth: Int, acc: List[Int]): (Boolean, List[Int]) =
      if keyWord == (wordPart + current.item) then (true, current.index :: acc)
      else if wordPart.length >= keyWord.length then (false, List.empty)
      else if !current.neighbors.contains(direction) then (false, List.empty)
      else if wordPart.nonEmpty && !keyWord.startsWith(wordPart) then (false, List.empty)
      else searchDirection(grid.getCell(current.neighbors(direction)).get, direction, wordPart + current.item, grid, depth + 1, current.index :: acc)
    end searchDirection


    def searchGrid(start: Int, grid: Grid[String]): Int =
      val startCell = grid.getCell(start).get
      startCell.neighbors.keys.filter(onlyDiagonal).count(direction => {

        val (result, indexes) = searchDirection(startCell, direction, "", grid, 0, List.empty)
        matchedIndexes = matchedIndexes ++ indexes
        result
      })
    end searchGrid

    val (grid, roots) = fillGrid(input, new Grid[String](input.head.length, input.size))
    roots.foreach(root =>  searchGrid(root, grid))

    var count = matchedIndexes.filter(index => grid.getCell(index).get.item == "A").groupBy(identity).values.count(_.size == 2)

    var a = 0
    for y <- 0 until grid.y do
      println("")
      for x <- 0 until grid.x do
        if matchedIndexes.contains(a) && grid.getCell(a).get.item == "A" then
          print("A")
        else print (".")
        a += 1
      end for
    end for

    count