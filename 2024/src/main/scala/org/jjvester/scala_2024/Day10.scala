package org.jjvester.scala_2024

import org.jjvester.scala_2024.Neighbor.{EAST, NORTH, SOUTH, WEST}

import scala.annotation.tailrec
import scala.collection.mutable

private[scala_2024] class Day10:

  private val forks: mutable.Stack[(Cell[Int], List[Cell[Int]])] = mutable.Stack.empty
  private var validPaths: List[List[Cell[Int]]] = List.empty
  private var finalPositionsByTrailHead: Map[Int, List[Int]] = Map.empty

  private def fillRow(input: String, grid: Grid[Int], index: Int): Grid[Int] =
    input.zipWithIndex.foreach((item, idx) => grid.insert(item.toString.toInt, index + idx))
    grid

  @tailrec
  private def fillGrid(input: List[String], grid: Grid[Int], index: Int = 0): Grid[Int] =
    if input.isEmpty then grid
    else fillGrid(input.tail, fillRow(input.head, grid, index), index + grid.x)

  private def validNextPaths(current: Cell[Int], grid: Grid[Int]): List[Cell[Int]] =
    current.neighbors.filter((neighbor, _) => neighbor == NORTH || neighbor == SOUTH || neighbor == WEST || neighbor == EAST)
      .map((_, index) => grid.getCell(index).get).filter(cell => cell.item == current.item + 1).toList

  private def visited(next: Cell[Int], path: List[Cell[Int]]) = path.exists(_.index == next.index)

  private def traversePath(current: Cell[Int], grid: Grid[Int], path: List[Cell[Int]]): Unit =
    if path.size == 10 then
//      path.foreach(item => print(item.item))
//      print(":")
//      path.foreach(item => print(s"${item.index} "))
//      println("")
      validPaths = path :: validPaths
      finalPositionsByTrailHead = finalPositionsByTrailHead.updatedWith(path.reverse.head.index)(positions => Option(path.head.index :: positions.getOrElse(List.empty)))
    else
      val neighbors = validNextPaths(current, grid).filter(!visited(_, path))
      neighbors.foreach(neighbor => forks.push((neighbor, neighbor :: path)))

  private def discover(grid: Grid[Int]): Unit =
    while forks.nonEmpty do
      val (current, path) = forks.pop()
     // println(current)

      traversePath(current, grid, path)
    end while

  end discover


  def part1(input: List[String]): Int =
    val grid = fillGrid(input, new Grid[Int](input.head.length, input.size))
    val trailHeads = (0 until (grid.x * grid.y)).filter(grid.getCell(_).get.item == 0).map(index => grid.getCell(index).get).toList

   // grid.draw()
   // println(trailHeads)

    trailHeads.foreach(trailHead => forks.push((trailHead, List(trailHead))))

    discover(grid)

    finalPositionsByTrailHead.foreach{ (key, value) =>
      println(s"$key ${value.distinct}")
    }

    finalPositionsByTrailHead.map((_, value) => value.distinct.size).sum

  def part2(input: List[String]): Int =
    val positionsByTrailHead = part1(input)
    validPaths.size