package org.jjvester

import scala.annotation.tailrec
import scala.collection.mutable

// Note: Because i have not made the findPath#recur tail recursive, the stack must be increased for this solution to work. -Xss 3m should do the trick
//TODO: Will refactor it to be tail recursive or be explicit loop
object Day12:

  private final class Topology(val grid: List[List[Node]], val rows: Int, val cols: Int, var start: Node, var end: Node)
  private final class Node(var id: String, val idx: Int, var neighbors: List[Node], var cost: Int)

  private def point(topology: Topology, node: Node): (Int, Int) = if node.idx == 0 then (0, 0) else (node.idx % topology.cols, node.idx / topology.cols)
  private var path: List[Node] = List.empty

  private def left(topology: Topology, node: Node): Option[Node] =
    val (col, row) = point(topology, node)
    if col > 0 then Some(topology.grid(row)(col - 1)) else None

  private def right(topology: Topology, node: Node): Option[Node] =
    val (col, row) = point(topology, node)
    if col < topology.cols - 1 then Some(topology.grid(row)(col + 1)) else None

  private def top(topology: Topology, node: Node): Option[Node] =
    val (col, row) = point(topology, node)
    if row > 0 then Some(topology.grid(row - 1)(col)) else None

  private def bottom(topology: Topology, node: Node): Option[Node] =
    val (col, row) = point(topology, node)
    if row < topology.rows - 1 then Some(topology.grid(row + 1)(col)) else None

  private def appendOrElse(neighbor: Option[Node], acc: List[Node]): List[Node] = neighbor match
      case Some(node) => node :: acc
      case None => acc

  private def neighbors(topology: Topology, node: Node): Unit =
    node.neighbors = appendOrElse(left(topology, node), node.neighbors)
    node.neighbors = appendOrElse(right(topology, node), node.neighbors)
    node.neighbors = appendOrElse(top(topology, node), node.neighbors)
    node.neighbors = appendOrElse(bottom(topology, node), node.neighbors)

  private def neighborhood(topology: Topology): Unit =
    @tailrec
    def recur(src: List[List[Node]]): Unit =
      if src.nonEmpty then
        val row = src.head
        row.foreach(col => neighbors(topology, col))
        recur(src.tail)

    recur(topology.grid)

  private def toGrid(input: List[String]): Topology =
    @tailrec
    def buildRow(src: String, acc: List[Node], idx: Int): List[Node] =
      if src.isEmpty then return acc
      buildRow(src.tail, Day12.Node(src.head.toString, idx, List.empty, Int.MaxValue) :: acc, idx + 1)

    @tailrec
    def build(src: List[String], acc: List[List[Node]], idx: Int): List[List[Node]] =
      if src.isEmpty then return acc

      val row = buildRow(src.head, List.empty, idx).reverse
      build(src.tail, buildRow(src.head, List.empty, idx).reverse :: acc, idx + row.size)

    val grid = build(input, List.empty, 0)
    Topology(grid.reverse, grid.size, grid.head.size, grid.flatten.find(node => node.id.equals("S")).get, grid.flatten.find(node => node.id.equals("E")).get)

  private def validNeighbors(node: Node, topology: Topology): List[Node] =
    if node.id.equals("S") then node.neighbors.filter(neighbor => 'a' == neighbor.id.head || 'a' + 1 == neighbor.id.head || neighbor.id.head < 'a' || neighbor == topology.end)
    else node.neighbors.filter(neighbor => node.id.head == neighbor.id.head || node.id.head + 1 == neighbor.id.head || neighbor.id.head < node.id.head || neighbor == topology.end)

  private def highestPoint(node: Node): Boolean = node.id.equals("z")
  private def target(node: Node, topology: Topology) = node == topology.end

  private def findPaths(topology: Topology): Unit =
    val start = topology.start
    start.cost = 0

    def recur(next: Node, current: Node, acc: List[Node], cost: Int): Unit =
      if highestPoint(current) && target(next, topology) then
        path = current :: acc
      else if cost < next.cost then
        next.cost = cost
        validNeighbors(next, topology).foreach(node => recur(node, next, current :: acc, cost + 1))

    validNeighbors(start, topology).foreach(node => recur(node, start, List.empty, 0))

  def part1(input: List[String]): Int =
    val topology = toGrid(input)
    neighborhood(topology)
    findPaths(topology)
    path.size

  private def allStartLocations(topology: Topology): List[Int] = topology.grid.flatten.filter(node => node.id.equals("a") || node.id.equals("S")).map(node => node.idx)

  def part2(input: List[String]): Int =
    var topology = toGrid(input)
    neighborhood(topology)

    val result = allStartLocations(topology).map(idx =>
      topology = toGrid(input)
      neighborhood(topology)
      path = List.empty

      val startNode = topology.grid(idx / topology.cols)(idx % topology.cols)
      if !startNode.id.equals("S") then
        startNode.id = "S"
        topology.start.id = "a"
        topology.start = startNode

      findPaths(topology)
      path.size
    )

    result.filter(item => item > 0).min