package org.jjvester.scala_2024

import org.jjvester.scala_2024.Neighbor
import org.junit.Assert.assertTrue

class GridTest extends munit.FunSuite:

  val grid: Grid[Int] = new Grid[Int](5, 5)

  override def beforeAll(): Unit =
    for x <- 0 until 25 do
      grid.insert(x, x)

  test("topLeft") {
    val topLeft = grid.getCell(0).get

    assertEquals(3, topLeft.neighbors.size)
    assertTrue(AssertionUtilities.assertListEquals(topLeft.neighbors.values.toList, List(1, 6, 5)))
    assertTrue(AssertionUtilities.assertListEquals(topLeft.neighbors.keys.toList, List(Neighbor.EAST, Neighbor.SOUTH_EAST, Neighbor.SOUTH)))

    assertEquals(topLeft.neighbors(Neighbor.EAST), 1)
    assertEquals(topLeft.neighbors(Neighbor.SOUTH_EAST), 6)
    assertEquals(topLeft.neighbors(Neighbor.SOUTH), 5)
  }

  test("topRight") {
    val topRight = grid.getCell(4).get

    assertEquals(3, topRight.neighbors.size)
    assertTrue(AssertionUtilities.assertListEquals(topRight.neighbors.values.toList, List(3, 8, 9)))
    assertTrue(AssertionUtilities.assertListEquals(topRight.neighbors.keys.toList, List(Neighbor.WEST, Neighbor.SOUTH_WEST, Neighbor.SOUTH)))

    assertEquals(topRight.neighbors(Neighbor.WEST), 3)
    assertEquals(topRight.neighbors(Neighbor.SOUTH_WEST), 8)
    assertEquals(topRight.neighbors(Neighbor.SOUTH), 9)
  }

  test("topRow") {
    val topRow = grid.getCell(1).get

    assertEquals(5, topRow.neighbors.size)
    assertTrue(AssertionUtilities.assertListEquals(topRow.neighbors.values.toList, List(0, 2, 5, 6, 7)))
    assertTrue(AssertionUtilities.assertListEquals(topRow.neighbors.keys.toList, List(Neighbor.WEST, Neighbor.EAST, Neighbor.SOUTH, Neighbor.SOUTH_WEST, Neighbor.SOUTH_EAST)))

    assertEquals(topRow.neighbors(Neighbor.WEST), 0)
    assertEquals(topRow.neighbors(Neighbor.EAST), 2)
    assertEquals(topRow.neighbors(Neighbor.SOUTH), 6)
    assertEquals(topRow.neighbors(Neighbor.SOUTH_WEST), 5)
    assertEquals(topRow.neighbors(Neighbor.SOUTH_EAST), 7)
  }

  test("left") {
    val left = grid.getCell(10).get

    assertEquals(5, left.neighbors.size)
    assertTrue(AssertionUtilities.assertListEquals(left.neighbors.values.toList, List(5, 6, 11, 16, 15)))
    assertTrue(AssertionUtilities.assertListEquals(left.neighbors.keys.toList, List(Neighbor.EAST, Neighbor.SOUTH, Neighbor.NORTH, Neighbor.SOUTH_EAST, Neighbor.NORTH_EAST)))

    assertEquals(left.neighbors(Neighbor.NORTH), 5)
    assertEquals(left.neighbors(Neighbor.NORTH_EAST), 6)
    assertEquals(left.neighbors(Neighbor.EAST), 11)
    assertEquals(left.neighbors(Neighbor.SOUTH_EAST), 16)
    assertEquals(left.neighbors(Neighbor.SOUTH), 15)
  }

  test("right") {
    val right = grid.getCell(14).get

    assertEquals(5, right.neighbors.size)
    assertTrue(AssertionUtilities.assertListEquals(right.neighbors.values.toList, List(9, 8, 13, 18, 19)))
    assertTrue(AssertionUtilities.assertListEquals(right.neighbors.keys.toList, List(Neighbor.WEST, Neighbor.SOUTH, Neighbor.NORTH, Neighbor.SOUTH_WEST, Neighbor.NORTH_WEST)))

    assertEquals(right.neighbors(Neighbor.NORTH), 9)
    assertEquals(right.neighbors(Neighbor.NORTH_WEST), 8)
    assertEquals(right.neighbors(Neighbor.WEST), 13)
    assertEquals(right.neighbors(Neighbor.SOUTH_WEST), 18)
    assertEquals(right.neighbors(Neighbor.SOUTH), 19)
  }

  test("bottomRight") {
    val bottomRight = grid.getCell(24).get

    assertEquals(3, bottomRight.neighbors.size)
    assertTrue(AssertionUtilities.assertListEquals(bottomRight.neighbors.values.toList, List(19, 18, 23)))
    assertTrue(AssertionUtilities.assertListEquals(bottomRight.neighbors.keys.toList, List(Neighbor.WEST, Neighbor.NORTH_WEST, Neighbor.NORTH)))

    assertEquals(bottomRight.neighbors(Neighbor.NORTH), 19)
    assertEquals(bottomRight.neighbors(Neighbor.NORTH_WEST), 18)
    assertEquals(bottomRight.neighbors(Neighbor.WEST), 23)
  }

  test("bottomLeft") {
    val bottomLeft = grid.getCell(20).get

    assertEquals(3, bottomLeft.neighbors.size)
    assertTrue(AssertionUtilities.assertListEquals(bottomLeft.neighbors.values.toList, List(15, 16, 21)))
    assertTrue(AssertionUtilities.assertListEquals(bottomLeft.neighbors.keys.toList, List(Neighbor.EAST, Neighbor.NORTH_EAST, Neighbor.NORTH)))

    assertEquals(bottomLeft.neighbors(Neighbor.NORTH), 15)
    assertEquals(bottomLeft.neighbors(Neighbor.NORTH_EAST), 16)
    assertEquals(bottomLeft.neighbors(Neighbor.EAST), 21)
  }

  test("bottomRow") {
    val bottomRow = grid.getCell(22).get

    assertEquals(5, bottomRow.neighbors.size)
    assertTrue(AssertionUtilities.assertListEquals(bottomRow.neighbors.values.toList, List(21, 16, 17, 18, 23)))
    assertTrue(AssertionUtilities.assertListEquals(bottomRow.neighbors.keys.toList, List(Neighbor.EAST, Neighbor.WEST, Neighbor.NORTH, Neighbor.NORTH_EAST, Neighbor.NORTH_WEST)))

    assertEquals(bottomRow.neighbors(Neighbor.WEST), 21)
    assertEquals(bottomRow.neighbors(Neighbor.EAST), 23)
    assertEquals(bottomRow.neighbors(Neighbor.NORTH), 17)
    assertEquals(bottomRow.neighbors(Neighbor.NORTH_WEST), 16)
    assertEquals(bottomRow.neighbors(Neighbor.NORTH_EAST), 18)
  }

  test("middle") {
    val middle = grid.getCell(12).get

    assertEquals(8, middle.neighbors.size)
    assertTrue(AssertionUtilities.assertListEquals(middle.neighbors.values.toList, List(6, 7, 8, 11, 13, 16, 17, 18)))
    assertTrue(AssertionUtilities.assertListEquals(middle.neighbors.keys.toList, List(Neighbor.EAST, Neighbor.WEST, Neighbor.NORTH, Neighbor.SOUTH, Neighbor.NORTH_EAST, Neighbor.NORTH_WEST, Neighbor.SOUTH_EAST, Neighbor. SOUTH_WEST)))

    assertEquals(middle.neighbors(Neighbor.WEST), 11)
    assertEquals(middle.neighbors(Neighbor.EAST), 13)
    assertEquals(middle.neighbors(Neighbor.NORTH), 7)
    assertEquals(middle.neighbors(Neighbor.SOUTH), 17)
    assertEquals(middle.neighbors(Neighbor.NORTH_WEST), 6)
    assertEquals(middle.neighbors(Neighbor.NORTH_EAST), 8)
    assertEquals(middle.neighbors(Neighbor.SOUTH_EAST), 18)
    assertEquals(middle.neighbors(Neighbor.SOUTH_WEST), 16)
  }
