package org.jjvester

import scala.annotation.tailrec
import scala.collection.mutable

final class Node(val name: String, var children: List[Node], var size: Int, var parent: Node)

final class Day7:
  private def isNavigation(input: String) = input.startsWith("$ cd")
  private def getTarget(input: String) = input.substring(input.lastIndexOf(" "))
  private def isListing(input: String) = input.startsWith("$ ls")
  private def isFile(input: String): Boolean = input.substring(0, input.indexOf(" ")).forall(_.isDigit)
  private def getFileName(input: String) = input.split(" ") (1)
  private def getSize(input: String): Int = if isFile(input) then Integer.valueOf(input.substring(0, input.indexOf(" "))) else 0

  // Shoot me!
  private var files: List[Node] = List.empty
  private var directories: List[Node] = List.empty

  @tailrec
  private def root(tree: Node): Node =
    if tree.parent == null then tree
    else root(tree.parent)

  private def execute(command: String, tree: Node): Node =
    if isNavigation(command) then
      val target = getTarget(command).trim
      target match
        case ".." => tree.parent
        case "/" => root(tree)
        case _ => tree.children.find(item => item.name.equals(target)).get // Yugh!
    else if isListing(command) then tree
    else
      // This is just dumb!
      val child = Node(getFileName(command), List.empty, getSize(command), tree)
      tree.children = child :: tree.children

      if isFile(command) then files = child :: files
      else directories = child :: directories

      tree

  // I was sick of this problem at this point
  private def sum(): Unit =
    files.filter(file => file.parent != null).foreach(file => file.parent.size += file.size)
    directories.filter(directory => directory.parent != null).foreach(directory =>  directory.parent.size += directory.size)

  private def makeFileSystem(input: List[String]): Node =
    val fileSystem: Node = Node("/", List.empty, 0, null)

    @tailrec
    def recur(src: List[String], tree: Node): Unit =
      if src.nonEmpty then recur(src.tail, execute(src.head, tree))

    recur(input, fileSystem)
    fileSystem

  def part1(input: List[String]): Int =
    makeFileSystem(input)
    sum()
    directories.filter(directory => directory.size <= 100_000).map(directory => directory.size).sum

  def part2(input: List[String]): Int =
    val fileSystem = makeFileSystem(input)
    sum()

    val currentAvailable = 70000000 - fileSystem.size
    val needed = 30000000 - currentAvailable

    @tailrec
    def getDeleteCandidate(directories: List[Node]): Node =
      if directories.head.size >= needed then directories.head
      else getDeleteCandidate(directories.tail)

    getDeleteCandidate(directories.sortBy(node => node.size)).size