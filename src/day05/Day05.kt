package day05

import println
import readInput
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Point(val x: Int, val y: Int)
data class Line(val start: Point, val end: Point)

fun main() {

    fun String.toCoords() = this.split(",").let { coords -> Point(coords[0].toInt(), coords[1].toInt()) }

    fun Line.isDiagonal() = this.start.x != this.end.x && this.start.y != this.end.y

    fun part1(input: List<String>): Int {
        val lines = input.map { line -> line.split(" -> ").let { Line(it[0].toCoords(), it[1].toCoords()) } }
        val points = mutableMapOf<Point, Int>()
        lines.forEach { line ->
            if (!line.isDiagonal()) {
                for (x in min(line.start.x, line.end.x)..max(line.start.x, line.end.x))
                    for (y in min(line.start.y, line.end.y)..max(line.start.y, line.end.y))
                        points[Point(x, y)] = points.getOrDefault(Point(x, y), 0) + 1
            }
        }
        return points.values.count { it > 1 }
    }

    fun part2(input: List<String>): Int {
        val lines = input.map { line -> line.split(" -> ").let { Line(it[0].toCoords(), it[1].toCoords()) } }
        val points = mutableMapOf<Point, Int>()
        lines.forEach { line ->
            if (!line.isDiagonal()) {
                for (x in min(line.start.x, line.end.x)..max(line.start.x, line.end.x))
                    for (y in min(line.start.y, line.end.y)..max(line.start.y, line.end.y))
                        points[Point(x, y)] = points.getOrDefault(Point(x, y), 0) + 1
            } else {
                val distX = line.end.x - line.start.x
                val distY = line.end.y - line.start.y
                for (inc in 0..abs(distX)) {
                    val x = if (distX < 0) line.start.x - inc else line.start.x + inc
                    val y = if (distY < 0) line.start.y - inc else line.start.y + inc
                    points[Point(x, y)] = points.getOrDefault(Point(x, y), 0) + 1
                }
            }
        }
        return points.values.count { it > 1 }
    }

    val testInput = readInput("day05/Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("day05/Day05")
    part1(input).println()
    part2(input).println()
}
