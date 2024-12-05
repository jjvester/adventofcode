package org.jjvester.scala_2024

private[scala_2024] object AssertionUtilities:
  def assertListEquals[T](actual: List[T], expected: List[T]): Boolean = actual.size == expected.size && actual.forall(expected.contains(_))
