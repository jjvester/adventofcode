package org.jjvester

import scala.annotation.tailrec

final class Point(val height: Int, val index: Int, val edge: Boolean, var visible: Boolean, var score: Long)

object Day8:
  private def isEdge(index: Int, rowIndex: Int, rowLength: Int, maxRows: Int): Boolean =
    val bottomRow = Range((rowLength * (maxRows - 1)) + 1, rowLength * maxRows)
    index < rowLength || rowIndex == 0 || rowIndex == rowLength - 1 || bottomRow.contains(index)

  @tailrec
  private def computeSceneryScore(grid: List[List[Point]], point: Point, walkOp: Int => Int, index: Int, maxRows: Int, acc: Int): Int =
    val nextIndex = walkOp(index)
    val nextPoint = grid(nextIndex / maxRows)(nextIndex % maxRows)
    if nextPoint.edge && nextPoint.height < point.height then acc + 1
    else if nextPoint.height >= point.height then acc + 1
    else computeSceneryScore(grid, point, walkOp, nextIndex, maxRows, acc + 1)

  @tailrec
  private def checkIsVisible(grid: List[List[Point]], point: Point, walkOp: Int => Int, index: Int, maxRows: Int): Boolean =
    val nextIndex = walkOp(index)
    val nextPoint = grid(nextIndex / maxRows)(nextIndex % maxRows)
    if nextPoint.edge then nextPoint.height < point.height
    else if nextPoint.height >= point.height then false
    else checkIsVisible(grid, point, walkOp, nextIndex, maxRows)

  private def isVisibleFromAllDirections(point: Point, grid: List[List[Point]], maxRows: Int, maxRowLength: Int) =
    checkIsVisible(grid, point, x => x + 1, point.index, maxRows) ||
      checkIsVisible(grid, point, x => x - 1, point.index, maxRows) ||
      checkIsVisible(grid, point, x => x + maxRowLength, point.index, maxRows) ||
      checkIsVisible(grid, point, x => x - maxRowLength, point.index, maxRows)

  @tailrec
  private def collectVisible(nodes: List[Point], acc: List[Point], grid: List[List[Point]], maxRows: Int, maxRowLength: Int): List[Point] =
    if nodes.isEmpty then return acc

    if !nodes.head.visible then
      if isVisibleFromAllDirections(nodes.head, grid, maxRows, maxRowLength) then
        nodes.head.visible = true
        collectVisible(nodes.tail, nodes.head :: acc, grid, maxRows, maxRowLength)
      else collectVisible(nodes.tail, acc, grid, maxRows, maxRowLength)
    else collectVisible(nodes.tail, nodes.head :: acc, grid, maxRows, maxRowLength)

  private def buildGrid(input: List[String], rowLength: Int, maxRows: Int): List[List[Point]] =
    @tailrec
    def recurRow(src: String, index: Int, rowIndex: Int, row: List[Point]): List[Point] =
      if src.isEmpty then return row
      val edgePoint = isEdge(index, rowIndex, rowLength, maxRows)
      recurRow(src.tail, index + 1, rowIndex + 1, Point(Integer.valueOf(src.head.toString), index, edgePoint, edgePoint, 0) :: row)

    @tailrec
    def recur(src: List[String], index: Int, acc: List[List[Point]]): List[List[Point]] =
      if src.isEmpty then return acc

      val newRow = recurRow(src.head, index, 0, List.empty)
      recur(src.tail, index + rowLength, newRow.reverse :: acc)

    val grid = recur(input, 0, List.empty)
    grid.reverse

  def part1(input: List[String]): Int =
    val rowLength = input.head.length
    val maxRows = input.size
    val grid = buildGrid(input, rowLength, maxRows)
    val visible = grid.flatten.filter(point => point.visible)
    val toCheck = grid.flatten.filter(point => !point.visible)
    val updatedVisible = collectVisible(toCheck, List.empty, grid, maxRows, rowLength)

//    grid.foreach(row =>
//      row.foreach(col => print("(" + col.height + "," + col.index + "," + col.edge + "," + col.visible + "),"))
//      println("\n")
//    )

    visible.size + updatedVisible.size

  def part2(input: List[String]): Long =
    val rowLength = input.head.length
    val maxRows = input.size
    val grid = buildGrid(input, rowLength, maxRows)
    val nonEdges = grid.flatten.filter(point => !point.visible)

    @tailrec
    def recur(input: List[Point], grid: List[List[Point]]): Unit =
      if input.nonEmpty then
        val point = input.head
        val right = computeSceneryScore(grid, point, x => x + 1, point.index, maxRows, 0)
        val left = computeSceneryScore(grid, point, x => x - 1, point.index, maxRows, 0)
        val top = computeSceneryScore(grid, point, x => x - rowLength, point.index, maxRows, 0)
        val bottom = computeSceneryScore(grid, point, x => x + rowLength, point.index, maxRows, 0)
        point.score = top * bottom * left * right
        recur(input.tail, grid)

    recur(nonEdges, grid)
    val sorted = nonEdges.sortBy(point => point.score).reverse
    sorted.head.score