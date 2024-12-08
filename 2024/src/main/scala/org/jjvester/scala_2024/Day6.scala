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

  private def buildGrid(input: List[String]): Grid[String] =
    val y = input.size
    val x = input.head.length
    fillGrid(input, new Grid[String](x, y))

  private def findGuard(grid: Grid[String]) = grid.getCell( (0 until (grid.x * grid.y)).find(index => isGuard(grid.getCell(index).get.item)).get).get

  def part1(input: List[String]): Int =
    val grid = buildGrid(input)
    val guard = findGuard(grid)

    val patrolPath = patrol(guard, currentDirection(guard.item), grid)
    patrolPath.distinct.size

  private def isCycle(current: Cell[String], direction: Neighbor, visited: Map[Int, Set[Neighbor]]): Boolean =
    if visited.contains(current.index) then
      val directionsTraversed = visited(current.index)
      directionsTraversed.contains(direction)
    else false

  private def updateVisits(current: Cell[String], direction: Neighbor, visited: Map[Int, Set[Neighbor]]): Map[Int, Set[Neighbor]] =
    visited.updatedWith(current.index)(visits => Option(visits.getOrElse(Set.empty) + direction))

  @tailrec
  private def patrolRecordingVisits(current: Cell[String], direction: Neighbor, grid: Grid[String], visited: Map[Int, Set[Neighbor]] = Map.empty): Boolean =
    if atEnd(current, direction) && !isObstacle(current) then true
    else if isCycle(current, direction, visited) then false
    else if isObstacle(next(current, direction, grid)) then patrolRecordingVisits(current, turnRight(current, direction, grid), grid, updateVisits(current, direction, visited))
    else patrolRecordingVisits(next(current, direction, grid), direction, grid, updateVisits(current, direction, visited))

  def part2(input: List[String]): Int =
    val grid = buildGrid(input)
    val startLocation = findGuard(grid)
    val patrolPath = patrol(startLocation, currentDirection(startLocation.item), grid).distinct

    var candidates: Set[Int] = Set.empty
    patrolPath.foreach{ index =>

      if index != startLocation.index then
        grid.insert("#", index)
        if !patrolRecordingVisits(startLocation, currentDirection(startLocation.item), grid) then
          candidates = candidates + index

        grid.insert(".", index)
    }
    candidates.size