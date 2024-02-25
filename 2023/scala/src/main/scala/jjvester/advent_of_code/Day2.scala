package jjvester.advent_of_code

import scala.annotation.{tailrec, targetName}

private[advent_of_code] class Day2(val red: Int, val green: Int, val blue: Int):
  // Game 5
  private def getGameId(src: String): Int = src.split(":").head.split(" ").tail.head.toInt
  // 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
  private def getBundles(src: String): List[String] = src.split(":")(1).split(";").toList
  // 6 red
  private def colorWithCount(src: String): (String, Int) = (src.split(" ").tail.head.trim, src.split(" ").head.trim.toInt)

  private def isValidColor(colorWithCount: (String, Int)): Boolean = colorWithCount._1 match
    case "red" => colorWithCount._2 <= red
    case "green" => colorWithCount._2 <= green
    case "blue" => colorWithCount._2 <= blue
    case _ => throw new IllegalStateException("Unsupported color")

  private def updateColor(colorWithCount: (String, Int), colorCounts: (Int, Int, Int)): (Int, Int, Int) = colorWithCount._1 match
    case "red" => (Math.max(colorWithCount._2, colorCounts._1), colorCounts._2, colorCounts._3)
    case "green" => (colorCounts._1, Math.max(colorWithCount._2, colorCounts._2), colorCounts._3)
    case "blue" => (colorCounts._1, colorCounts._2, Math.max(colorWithCount._2, colorCounts._3))
    case _ => throw new IllegalStateException("Unsupported color")

  // 6 red, 1 blue, 3 green
  private def isBundleValid(bundle: String): Boolean = recur(bundle.split(",").toList)
    @tailrec
    private def recur(src: List[String]) : Boolean =
      if src.isEmpty then true
      else if isValidColor(colorWithCount(src.head.trim)) then recur(src.tail)
      else false

  @tailrec
  private def isGameValid(bundles: List[String]): Boolean =
    if bundles.isEmpty then true
    else if isBundleValid(bundles.head) then isGameValid(bundles.tail)
    else false

  @tailrec
  private def cubePowerPerBundle(bundle: List[String], maxColorsPerCube: (Int, Int, Int)): (Int, Int, Int) =
    if bundle.isEmpty then maxColorsPerCube
    else cubePowerPerBundle(bundle.tail, updateColor(colorWithCount(bundle.head.trim), maxColorsPerCube))

  @tailrec
  private def cubePowerPerGame(bundles: List[String], maxColorsPerCube: (Int, Int, Int) = (0, 0, 0)): Int =
    if bundles.isEmpty then maxColorsPerCube._1 * maxColorsPerCube._2 * maxColorsPerCube._3
    else cubePowerPerGame(bundles.tail, cubePowerPerBundle(bundles.head.split(",").toList, maxColorsPerCube))

  // Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
  private[advent_of_code] def part1(input: List[String]): Int = findValidGames(input, List.empty)
    @tailrec
    private def findValidGames(src: List[String], acc: List[Int]): Int =
      if src.isEmpty then acc.sum
      else if isGameValid(getBundles(src.head)) then findValidGames(src.tail, getGameId(src.head) :: acc)
      else findValidGames(src.tail, acc)

  // Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
  private[advent_of_code] def part2(input: List[String]): Int = computeCubePowerPerGame(input, List.empty)
    @tailrec
    private def computeCubePowerPerGame(src: List[String], acc: List[Int]): Int =
      if src.isEmpty then acc.sum
      else computeCubePowerPerGame(src.tail, cubePowerPerGame(getBundles(src.head)) :: acc)