package jjvester

package object advent_of_code:

  private[advent_of_code] class Matrix(val x: Int, val y: Int):
    private def isTop(idx: Int): Boolean = idx >= 0 && idx < x
    private def isBottom(idx: Int): Boolean = idx >= x * (x - 1) && idx < x * x
    private def isLeft(idx: Int): Boolean = idx % x == 0

    private def isRight(idx: Int): Boolean = isLeft(idx - (x - 1))
    private def isTopLeft(idx: Int): Boolean = isTop(idx) && isLeft(idx)
    private def isBottomLeft(idx: Int): Boolean = isBottom(idx) && isLeft(idx)
    private def isTopRight(idx: Int): Boolean = isTop(idx) && isRight(idx)
    private def isBottomRight(idx: Int): Boolean = isBottom(idx) && isRight(idx)

    def draw(): Unit =
      var idx = 0
      for row <- 0 until y do
        for col <- 0 until x do
          print(s"$idx\t")
          idx += 1
        println("")

    def adjacent(idx: Int): List[Int] =
      if isTopLeft(idx) then List(idx + 1, idx + x, idx + x + 1)
      else if isTopRight(idx) then List(idx - 1, idx + x - 1, idx + x)
      else if isBottomLeft(idx) then List(idx + 1, idx - x, idx - (x - 1))
      else if isBottomRight(idx) then List(idx - 1, idx - x, idx - (x + 1))
      else if isTop(idx) then List(idx + 1, idx - 1, idx + x, idx + x + 1, idx + x - 1)
      else if isBottom(idx) then List(idx + 1, idx - 1, idx - x, idx - x - 1, idx - x + 1)
      else if isLeft(idx) then List(idx + 1, idx + x, idx + x + 1, idx - x, idx - x + 1)
      else if isRight(idx) then List(idx - 1, idx - x, idx - x - 1, idx + x, idx + x - 1)
      else List(idx - 1, idx + 1, idx + x, idx - x, idx + x - 1, idx + x + 1, idx - x - 1, idx - x + 1)
  end Matrix
