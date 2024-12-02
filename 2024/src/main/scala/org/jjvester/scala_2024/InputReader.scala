package org.jjvester.scala_2024

import scala.io.Source
import scala.util.Using

private[scala_2024]object InputReader:
  private[scala_2024] def readInput(path: String): List[String] = Using(Source.fromFile(path)){_.getLines.toList}.get
