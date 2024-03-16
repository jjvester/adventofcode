package jjvester.advent_of_code

import scala.annotation.tailrec

private[advent_of_code] object Day6:
  private final class Race(val time: Long, val distance: Long)

  @tailrec
  private def collectRaceResult(row: Array[String], acc: List[Long] = List.empty): List[Long] =
    if row.isEmpty then acc
    else collectRaceResult(row.tail, row.head.trim.toLong :: acc)

  @tailrec
  private def toRaces(times: List[Long], distances: List[Long], acc: List[Race] = List.empty): List[Race] =
    if times.isEmpty && distances.isEmpty then acc
    else toRaces(times.tail, distances.tail, new Race(times.head, distances.head) :: acc)

  private def isWin(race: Race, buttonDuration: Long): Boolean = buttonDuration * (race.time - buttonDuration) > race.distance

  private def wins(race: Race): Long = recur(race, 0, race.time)
    @tailrec
    private def recur(race: Race, start: Long, end: Long, acc: Long = 0): Long =
      if start == end && isWin(race, start) then acc + 1
      else if start >= end then acc
      else if isWin(race, start) && isWin(race, end) then recur(race, start + 1, end - 1, acc + 2)
      else if isWin(race, start) then recur(race, start + 1, end - 1, acc + 1)
      else if isWin(race, end) then recur(race, start + 1, end - 1, acc + 1)
      else recur(race, start + 1, end - 1, acc)

  def part2(input: List[String]): Long =
    val timeRow = collectRaceResult(input.head.split(":").tail.head.trim.split(" ").filter(item => item != "")).reverse.mkString
    val distanceRow = collectRaceResult(input.tail.head.split(":").tail.head.trim.split(" ").filter(item => item != "")).reverse.mkString

    val races = toRaces(List(timeRow.toLong), List(distanceRow.toLong))
    races.map(race => wins(race)).product

  def part1(input: List[String]): Long =
    val timeRow = collectRaceResult(input.head.split(":").tail.head.trim.split(" ").filter(item => item != ""))
    val distanceRow = collectRaceResult(input.tail.head.split(":").tail.head.trim.split(" ").filter(item => item != ""))

    if timeRow.length != distanceRow.length then throw new IllegalArgumentException("time and distance input should be same size")

    val races = toRaces(timeRow, distanceRow)
    races.map(race => wins(race)).product