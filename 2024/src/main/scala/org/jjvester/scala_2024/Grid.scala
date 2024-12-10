package org.jjvester.scala_2024

private[scala_2024] enum Neighbor:
  case NORTH, SOUTH, WEST, EAST, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST
end Neighbor

private[scala_2024] class CellBuilder[T](val item: T, val index: Int):
  private var neighbors = Map[Neighbor, Int]()

  def withNeighbors(neighbors: Map[Neighbor, Int]): this.type =
    this.neighbors = neighbors
    this

  def build(): Cell[T] = new Cell(item, index, neighbors)
end CellBuilder

private[scala_2024] class Cell[T](val item: T, val index: Int, val neighbors: Map[Neighbor, Int])

private[scala_2024] class Grid[T](val x: Int, val y: Int):
  private var items = Map[Int, Cell[T]]()

  def insert(item: T, index: Int): Unit =
    var cell = new CellBuilder[T](item, index).withNeighbors(getNeighbors(index)).build()
    items = items.updated(index, cell)

  def getCell(index: Int): Option[Cell[T]] = items.get(index)

  def draw(): Unit =
    var index = 0
    for y <- 0 until y do
      println("")
      for x <- 0 until x do
        print(items(index).item)
        index += 1
      end for
    end for
  end draw

  private def isTopRow(index: Int) = index >= 0 && index < x
  private def isBottomRow(index: Int) = index >= (x * y - x) && index < (x * y - 1)
  private def isLeft(index: Int) = index % x == 0
  private def isRight(index: Int) = isLeft(index - x + 1)
  private def isTopLeft(index: Int) = index == 0
  private def isTopRight(index: Int) = index == x - 1
  private def isBottomLeft(index: Int) = index == (x * y - x)
  private def isBottomRight(index: Int) = index == (x * y - 1)

  private def getNeighbors(index: Int): Map[Neighbor, Int] =
    if isTopLeft(index) then Map(Neighbor.EAST -> (index + 1), Neighbor.SOUTH -> (index + x), Neighbor.SOUTH_EAST -> (index + x + 1))
    else if isTopRight(index) then Map(Neighbor.WEST -> (index - 1), Neighbor.SOUTH -> (index + x), Neighbor.SOUTH_WEST -> (index + x - 1))
    else if isBottomLeft(index) then Map(Neighbor.EAST -> (index + 1), Neighbor.NORTH -> (index - x), Neighbor.NORTH_EAST -> (index - x + 1))
    else if isBottomRight(index) then Map(Neighbor.WEST -> (index - 1), Neighbor.NORTH -> (index - x), Neighbor.NORTH_WEST -> (index - x - 1))
    else if isTopRow(index) then Map(Neighbor.WEST -> (index - 1), Neighbor.EAST -> (index + 1), Neighbor.SOUTH -> (index + x), Neighbor.SOUTH_WEST -> (index + x - 1), Neighbor.SOUTH_EAST -> (index + x + 1))
    else if isBottomRow(index) then Map(Neighbor.WEST -> (index - 1), Neighbor.EAST -> (index + 1), Neighbor.NORTH -> (index - x), Neighbor.NORTH_WEST -> (index - x - 1), Neighbor.NORTH_EAST -> (index - x + 1))
    else if isLeft(index) then Map(Neighbor.NORTH -> (index - x), Neighbor.SOUTH -> (index + x), Neighbor.EAST -> (index + 1), Neighbor.NORTH_EAST -> (index - x + 1), Neighbor.SOUTH_EAST -> (index + x + 1))
    else if isRight(index) then Map(Neighbor.NORTH -> (index - x), Neighbor.SOUTH -> (index + x), Neighbor.WEST -> (index - 1), Neighbor.NORTH_WEST -> (index - x - 1), Neighbor.SOUTH_WEST -> (index + x - 1))
    else Map(Neighbor.NORTH -> (index - x),
             Neighbor.SOUTH -> (index + x),
             Neighbor.WEST -> (index - 1),
             Neighbor.EAST -> (index + 1),
             Neighbor.NORTH_EAST -> (index - x + 1),
             Neighbor.NORTH_WEST -> (index - x - 1),
             Neighbor.SOUTH_WEST -> (index + x - 1),
             Neighbor.SOUTH_EAST -> (index + x + 1))