package jjvester.advent_of_code

import scala.annotation.tailrec
import scala.collection.immutable.NumericRange

private[advent_of_code] object Day5:

  private final val seedLocationLookupOrder = List("seed-to-soil", "soil-to-fertilizer", "fertilizer-to-water", "water-to-light", "light-to-temperature", "temperature-to-humidity", "humidity-to-location")

  private sealed trait RangeMapping:
    val range: NumericRange[Long]

  private final class SrcRange(val range: NumericRange[Long]) extends RangeMapping
  private final class DstRange(val range: NumericRange[Long]) extends RangeMapping

  private def getSeeds(src: String) = src.split(":").tail.head.trim.split(" ").toList
  private def getMapName(src: String) = src.split(" ").head
  private def getSrcDestCount(src: String): List[Long] = src.split(" ").map(_.toLong).toList
  private def isMapNameHeader(src: String) = src.contains("map")
  private def toRanges(src: List[Long]): (RangeMapping, RangeMapping) = (new DstRange(src.head until (src.head + src(2))), new SrcRange(src(1) until (src(1) + src(2))))

  @tailrec
  private def collectMapping(src: List[String], name: String, acc: List[(RangeMapping, RangeMapping)]): (String, List[(RangeMapping, RangeMapping)], List[String]) =
    if src.isEmpty then (name, acc, src)
    else if src.head.isEmpty then (name, acc, src.tail)
    else collectMapping(src.tail, name, toRanges(getSrcDestCount(src.head)) :: acc)

//  private def draw(src: Map[String, List[(RangeMapping, RangeMapping)]]): Unit =
//    src.foreach(kv => {
//      prLongln(kv._1)
//      kv._2.foreach(item => prLongln(item._1.range.start + "->" + item._1.range.end + " : " + item._2.range.start + "->" + item._2.range.end))
//    })
//    prLongln("---")

  @tailrec
  private def collectMappings(src: List[String], acc: Map[String, List[(RangeMapping, RangeMapping)]] = Map.empty): Map[String, List[(RangeMapping, RangeMapping)]] =
    if src.isEmpty then acc
    else if isMapNameHeader(src.head) then
      val mapName = getMapName(src.head)
      val mapping = collectMapping(src.tail, mapName, List.empty)
      collectMappings(mapping._3, acc.updated(mapName, mapping._2))
    else collectMappings(src.tail, acc)

  private def getDestination(seed: Long, mappings: List[(RangeMapping, RangeMapping)]): Long =
    val x = mappings.filter(mapping => mapping._2.range.contains(seed))
    if x.isEmpty then seed
    else if x.size > 1 then throw new IllegalStateException("Should only be 1 match")
    else
      val dstRange = x.head._1
      val srcRange = x.head._2
      dstRange.range.start + (seed - srcRange.range.start)

  @tailrec
  private def findClosestSeed(seeds: List[String], mappings: Map[String, List[(RangeMapping, RangeMapping)]], location: Long = -1): Long =
    if seeds.isEmpty then location
    else
      var current = seeds.head.toLong
      seedLocationLookupOrder.foreach(seedMappingName => {
        val maps = mappings(seedMappingName)
       // println(seeds.head + ":" + seedMappingName)
        current = getDestination(current, maps)
       // println("location " + current)
      })
      findClosestSeed(seeds.tail, mappings, if location < 0 then current else Math.min(location, current))

  def part1(input: List[String]): Long =
    val seeds = getSeeds(input.head)
    val mappings = collectMappings(input.tail)
    findClosestSeed(seeds, mappings)

  @tailrec
  private def buildSeedRanges(src: List[String], start: String = "", end: String = "", acc: Set[Long] = Set.empty): Set[Long] =
    if src.isEmpty && start.isEmpty && end.isEmpty then acc
    else if start.nonEmpty && end.nonEmpty then buildSeedRanges(src, "", "", acc ++ (start.toLong until (start.toLong + end.toLong)).toSet)
    else if start.nonEmpty && end.isEmpty then buildSeedRanges(src.tail, start, src.head, acc)
    else buildSeedRanges(src.tail, src.head, end, acc)

  def part2(input: List[String]): Long =
    val seeds = getSeeds(input.head)
    val mappings = collectMappings(input.tail)
    val locations = mappings("humidity-to-location").flatMap(_._1.range.toList).distinct.sorted
    println(locations)
    0
