package org.jjvester.scala_2024

import scala.annotation.tailrec

private[scala_2024] object Day8:
  @tailrec
  private def fillGrid(input: List[String], grid: Grid[String], index: Int = 0): Grid[String] =
    if input.isEmpty then grid
    else
      input.head.zipWithIndex.foreach((item, idx) => grid.insert(item.toString, idx + index))
      fillGrid(input.tail, grid, index + grid.y)
  end fillGrid

  private def isAntenna(cell: Cell[String]) = cell.item.forall(_.isLetterOrDigit)
  private def findAllAntennas(grid: Grid[String]): Set[Int] = (0 until grid.y * grid.x).filter(grid.getCell(_).exists(isAntenna)).toSet
  private def isSameRow(a: Cell[String], b: Cell[String], grid: Grid[String]) = (a.index % grid.x) == (b.index % grid.x)

  @tailrec
  private def connectAntiNode(antenna: Cell[String], grid: Grid[String], other: List[Int]): Unit =
    if other.nonEmpty then
      println(s"Trying to connect ${antenna.index} with ${other.head}")
      val a = antenna
      val b = grid.getCell(other.head).get
      val diff = Math.abs(a.index - b.index)
      val aAntiNode = a.index - diff
      val bAntiNode = b.index + diff

      if isSameRow(a, b, grid) then println("same row")
        if isSameRow(a, grid.getCell(aAntiNode).get, grid) then
          println("place antiNode in same row")

        if isSameRow(b, grid.getCell(bAntiNode).get, grid) then
          println("place antiNode in same row")
      else

      
      connectAntiNode(antenna, grid, other.tail)

  def part1(input: List[String]): Int =
    val grid = fillGrid(input, new Grid[String](input.head.length, input.size))
    grid.draw()

    var antennaMap: Map[String, List[Int]] = Map.empty
    findAllAntennas(grid).foreach{ index =>
      val cell = grid.getCell(index).get
      antennaMap = antennaMap.updatedWith(cell.item)(cells => Option(index :: cells.getOrElse(List.empty)))
    }

    antennaMap.foreach((key, antennas) => {
      println(s"$key $antennas")
      var antennasSorted = antennas.sorted
      antennasSorted.foreach {antenna =>
        connectAntiNode(grid.getCell(antenna).get, grid, antennas.filter(_ != antenna))
      }

      grid.draw()
      println("")
    })

    0
