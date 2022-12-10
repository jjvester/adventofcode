package org.jjvester

import scala.::
import scala.annotation.tailrec
import scala.collection.mutable

object Day9:
  enum NodeType:
    case HEAD, TAIL
  final class Point(val x: Int, val y: Int)
  final class Knot(val nodeType: NodeType, var curr: Point, var touched: mutable.Set[String], val prev: Knot, var prevDirection: Direction)

  enum Direction:
    case R, L, U, D

  private def nextInstruction(input: String): (Direction, Int) =
    val direction = Direction.valueOf(input.split(" ")(0).trim)
    val steps = Integer.valueOf(input.split(" ")(1).trim)
    (direction, steps)

  private def nextPoint(direction: Direction, current: Day9.Point): Point =
    if direction == Direction.U then Day9.Point(current.x, current.y + 1)
    else if direction == Direction.D then Day9.Point(current.x, current.y - 1)
    else if direction == Direction.L then Day9.Point(current.x - 1, current.y)
    else Day9.Point(current.x + 1, current.y)

  private def isSameRow(a: Point, b: Point): Boolean = a.y == b.y
  private def isSameCol(a: Point, b: Point): Boolean = a.x == b.x
  private def isTooFar(a: Int, b: Int): Boolean = Math.abs(a - b) >= 2
  private def isHeadGreater(head: Int, tail: Int): Boolean = head > tail
  private def isHeadLess(head: Int, tail: Int): Boolean = head < tail

  private def nextTailPoint(head: Knot, tail: Knot, direction: Direction): Point =
    val headLocation = head.curr
    val tailLocation = tail.curr

    if isSameRow(headLocation, tailLocation) && isTooFar(headLocation.x, tailLocation.x) && isHeadGreater(headLocation.x, tailLocation.x) then nextPoint(Direction.R, tailLocation)
    else if isSameRow(headLocation, tailLocation) && isTooFar(headLocation.x, tailLocation.x) && isHeadLess(headLocation.x, tailLocation.x) then nextPoint(Direction.L, tailLocation)
    else if isSameCol(headLocation, tailLocation) && isTooFar(headLocation.y, tailLocation.y) && isHeadGreater(headLocation.y, tailLocation.y) then nextPoint(Direction.U, tailLocation)
    else if isSameCol(headLocation, tailLocation) && isTooFar(headLocation.y, tailLocation.y) && isHeadLess(headLocation.y, tailLocation.y) then nextPoint(Direction.D, tailLocation)
    else if !isSameRow(headLocation, tailLocation) && !isSameCol(head.curr, tailLocation) &&
      (isTooFar(headLocation.y, tailLocation.y) || isTooFar(headLocation.x, tailLocation.x)) then
      if headLocation.y > tailLocation.y then tail.curr = nextPoint(Day9.Direction.U, tail.curr)
      else tail.curr = nextPoint(Day9.Direction.D, tail.curr)

      if head.curr.x > tail.curr.x then
        moveKnot(tail, Day9.Direction.R, nextPoint(Day9.Direction.R, tail.curr))
      else moveKnot(tail, Day9.Direction.L, nextPoint(Day9.Direction.L, tail.curr))

      tail.curr
    else tail.curr

  @tailrec
  private def moveKnot(knot: Knot, direction: Direction, next: Point): Unit =
    knot.curr = next
    val key = "" + next.y + "" + next.x

    if knot.nodeType == NodeType.TAIL then
      knot.touched.add(key)
      if knot.prev != null then
        moveKnot(knot.prev, null, nextTailPoint(knot, knot.prev, null))

  @tailrec
  private def move(head: Knot, tail: Knot, direction: Direction, steps: Int): Unit =
    if steps > 0 then
      moveKnot(head, direction, nextPoint(direction, head.curr))
      moveKnot(tail, direction, nextTailPoint(head, tail, direction))
      move(head, tail, direction, steps - 1)

  def part1(input: List[String]): Int =
    @tailrec
    def recur(src: List[String], head: Knot, tail: Knot): Unit =
      if src.nonEmpty then
        val (direction, steps) = nextInstruction(src.head)
        move(head, tail, direction, steps)
        recur(src.tail, head, tail)

    val start = Day9.Point(0, 0)
    val key = "00"
    val head = Knot(NodeType.HEAD, start, mutable.Set(key), null, null)
    val tail = Knot(NodeType.TAIL, start, mutable.Set(key), null, null)
    recur(input, head, tail)
    tail.touched.size

  def part2(input: List[String], startPoint: Point): Int =
    @tailrec
    def recur(src: List[String], head: Knot, tail: Knot): Unit =
      if src.nonEmpty then
        val (direction, steps) = nextInstruction(src.head)
        move(head, tail, direction, steps)
        recur(src.tail, head, tail)

    val start = Day9.Point(startPoint.x, startPoint.y)
    val key = "" + start.y + "" + start.x
    val tail0 = Knot(NodeType.TAIL, start, mutable.Set(key), null, null)
    val tail1 = Knot(NodeType.TAIL, start, mutable.Set(key), tail0, null)
    val tail2 = Knot(NodeType.TAIL, start, mutable.Set(key), tail1, null)
    val tail3 = Knot(NodeType.TAIL, start, mutable.Set(key), tail2, null)
    val tail4 = Knot(NodeType.TAIL, start, mutable.Set(key), tail3, null)
    val tail5 = Knot(NodeType.TAIL, start, mutable.Set(key), tail4, null)
    val tail6 = Knot(NodeType.TAIL, start, mutable.Set(key), tail5, null)
    val tail7 = Knot(NodeType.TAIL, start, mutable.Set(key), tail6, null)
    val tail8 = Knot(NodeType.TAIL, start, mutable.Set(key), tail7, null)
    val head = Knot(NodeType.HEAD, start, mutable.Set(key), null, null)

    recur(input, head, tail8)
    tail0.touched.size
