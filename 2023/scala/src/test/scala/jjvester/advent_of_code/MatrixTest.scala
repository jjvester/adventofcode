package jjvester.advent_of_code

import scala.jdk.CollectionConverters._
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert

import java.util

class MatrixTest extends munit.FunSuite:

  private val matrix: Matrix = Matrix(5, 5)

  override def beforeEach(context: BeforeEach): Unit = matrix.draw()

  test("adjacent_topLeft") {
    assertThat(matrix.adjacent(0).asJavaCollection).hasSameElementsAs(List(1, 5, 6).asJavaCollection)
  }

  test("adjacent_topRight") {
    assertThat(matrix.adjacent(4).asJavaCollection).hasSameElementsAs(List(3, 9, 8).asJavaCollection)
  }

  test("adjacent_topRow") {
    assertThat(matrix.adjacent(1).asJavaCollection).hasSameElementsAs(List(0, 2, 7, 6, 5).asJavaCollection)
  }

  test("adjacent_left") {
    assertThat(matrix.adjacent(5).asJavaCollection).hasSameElementsAs(List(0, 10, 1, 11, 6).asJavaCollection)
  }

  test("adjacent_right") {
    assertThat(matrix.adjacent(9).asJavaCollection).hasSameElementsAs(List(4, 3, 8, 14, 13).asJavaCollection)
  }

  test("adjacent_bottomLeft") {
    assertThat(matrix.adjacent(20).asJavaCollection).hasSameElementsAs(List(21, 15, 16).asJavaCollection)
  }

  test("adjacent_bottomRight") {
    assertThat(matrix.adjacent(24).asJavaCollection).hasSameElementsAs(List(23, 19, 18).asJavaCollection)
  }

  test("adjacent_bottomRow") {
    assertThat(matrix.adjacent(21).asJavaCollection).hasSameElementsAs(List(20, 22, 15, 16, 17).asJavaCollection)
  }

  test("adjacent_middle") {
    assertThat(matrix.adjacent(16).asJavaCollection).hasSameElementsAs(List(15, 17, 11, 21, 10, 12, 20, 22).asJavaCollection)
  }