package org.jjvester

import scala.io.Source


object InputReader:
  def readInput(path: String): List[String] = Source.fromFile(path).getLines.toList
