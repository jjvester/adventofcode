package jjvester

package object advent_of_code:

  private[advent_of_code] class Matrix(val x: Int, val y: Int):
    private def isTop(idx: Int) = idx >= 0 && idx < x
    private def isBottom(idx: Int) = idx >= x * (x - 1) && idx < x * x
    private def isLeft(idx: Int) = idx % x == 0
    private def isRight(idx: Int) = idx.toString.endsWith((x - 1).toString)
    private def isTopLeft(idx: Int) = isTop(idx) && isLeft(idx)
    private def isBottomLeft(idx: Int) = isBottom(idx) && isLeft(idx)
    private def isTopRight(idx: Int) = isTop(idx) && isRight(idx)
    private def isBottomRight(idx: Int) = isBottom(idx) && isRight(idx)

    def adjacent(idx: Int): List[Int] =
      if isTopLeft(idx) then List(idx + 1, idx + x, idx + x + 1)
      else if isTopRight(idx) then List(idx - 1, idx + x - 1, idx + x)
      else if isBottomLeft(idx) then List(idx + 1, idx - x, idx - (x - 1))
      else if isBottomRight(idx) then List(idx - 1, idx - x, idx - (x + 1))
      else if isTop(idx) then List(idx + 1, idx - 1, idx + x, idx + x + 1, idx + x - 1)
      else if isBottom(idx) then List(idx + 1, idx - 1, idx - x, idx - x - 1, idx - x + 1)
      else if isLeft(idx) then List(idx + 1, idx + x, idx + x + 1, idx - (x - 1))
      else if isRight(idx) then List(idx - 1, idx - x, idx - x - 1, idx + x, idx + x - 1)
      else List(idx - 1, idx + 1, idx + x, idx - x, idx + x - 1, idx + x + 1, idx - x - 1, idx - (x + 1))
  end Matrix
