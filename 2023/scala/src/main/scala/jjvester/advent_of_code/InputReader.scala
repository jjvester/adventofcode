package jjvester.advent_of_code

import scala.io.Source
import scala.util.Using

private[advent_of_code]object InputReader:
  private[advent_of_code] def readInput(path: String): List[String] = Using(Source.fromFile(path)){_.getLines.toList}.get
