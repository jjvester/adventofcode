package org.jjvester.scala_2024

import org.jjvester.scala_2024.Neighbor.{NORTH, SOUTH, WEST, EAST}
import scala.annotation.tailrec

private[scala_2024] object Day6:

  @tailrec
  private def fillGrid(input: List[String], grid: Grid[String], index: Int = 0): Grid[String] =
    if input.isEmpty then grid
    else
      input.head.zipWithIndex.toList.foreach((item, rowIndex) => grid.insert(item.toString, index + rowIndex))
      fillGrid(input.tail, grid, index + grid.x)

  private def draw(grid: Grid[String]): Unit =
    var index = 0

    for y <- 0 until grid.y do
      println("")
      for x <- 0 until grid.x do
        print(grid.getCell(index).get.item)
        index += 1
      end for
    end for
  end draw

  private def turnRight(cell: Cell[String], currentDirection: Neighbor, grid: Grid[String]): Neighbor =
    currentDirection match
      case NORTH => EAST
      case EAST => SOUTH
      case SOUTH => WEST
      case WEST => NORTH
      case _ => throw new IllegalStateException("Invalid direction")

  private def currentDirection(item: String): Neighbor =
    item match
      case "^" => NORTH
      case ">" => EAST
      case "<" => WEST
      case "v" => SOUTH
      case _ => throw IllegalStateException("Unknown face direction")

  private def isGuard(item: String) = item == "^" || item == ">" || item == "<" || item == "v"
  private def atEnd(cell: Cell[String], direction: Neighbor): Boolean = !cell.neighbors.contains(direction)
  private def isObstacle(cell: Cell[String]) = cell.item == "#"
  private def next(current: Cell[String], direction: Neighbor, grid: Grid[String]): Cell[String] = grid.getCell(current.neighbors(direction)).get

  @tailrec
  private def patrol(current: Cell[String], direction: Neighbor, grid: Grid[String], traversed: List[Int] = Nil): List[Int] =
    if atEnd(current, direction) && !isObstacle(current) then current.index :: traversed
    else if isObstacle(next(current, direction, grid)) then patrol(current, turnRight(current, direction, grid), grid, traversed)
    else patrol(next(current, direction, grid), direction, grid, current.index :: traversed)

  @tailrec
  private def registerRightTurns(current: Cell[String], direction: Neighbor, grid: Grid[String], rightTurns: List[Int] = Nil): List[Int] =
    if atEnd(current, direction) && !isObstacle(current) then rightTurns
    else if isObstacle(next(current, direction, grid)) then registerRightTurns(current, turnRight(current, direction, grid), grid, current.index :: rightTurns)
    else registerRightTurns(next(current, direction, grid), direction, grid, rightTurns)

  private def buildGrid(input: List[String]): Grid[String] =
    val y = input.size
    val x = input.head.length
    fillGrid(input, new Grid[String](x, y))

  private def findGuard(grid: Grid[String]) = grid.getCell( (0 until (grid.x * grid.y)).find(index => isGuard(grid.getCell(index).get.item)).get).get

  def part1(input: List[String]): Int =
    val grid = buildGrid(input)
    val guard = findGuard(grid)

    draw(grid)
    val patrolPath = patrol(guard, currentDirection(guard.item), grid)
    patrolPath.distinct.size

  def part2(input: List[String]): Int =
    val grid = buildGrid(input)
    val guard = findGuard(grid)
    val rightTurns = registerRightTurns(guard, currentDirection(guard.item), grid)

    var index = 0
    for y <- 0 until grid.y do
      println("")
      for x <- 0 until grid.x do

        if rightTurns.contains(index) then print("R") else print(grid.getCell(index).get.item)
        index += 1
      end for
    end for
    0