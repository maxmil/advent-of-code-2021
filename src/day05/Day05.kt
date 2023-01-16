package day05

import println
import readInput
import kotlin.math.abs
import kotlin.math.max

data class Point(val x: Int, val y: Int)
data class Line(val start: Point, val end: Point)

fun main() {

    fun String.toCoords() = this.split(",").let { coords -> Point(coords[0].toInt(), coords[1].toInt()) }

    fun Line.isDiagonal() = this.start.x != this.end.x && this.start.y != this.end.y

    fun Line.addPoints(points: MutableMap<Point, Int>) {
        val distX = end.x - start.x
        val distY = end.y - start.y
        val steps = max(abs(distX), abs(distY))
        for (inc in 0..steps) {
            val x = start.x + distX * inc / steps
            val y = start.y + distY * inc / steps
            points[Point(x, y)] = points.getOrDefault(Point(x, y), 0) + 1
        }
    }

    fun dangerPoints(input: List<String>, predicate: (Line) -> Boolean = { true }): Int {
        val lines = input.map { line -> line.split(" -> ").let { Line(it[0].toCoords(), it[1].toCoords()) } }
        val points = mutableMapOf<Point, Int>()
        lines.filter(predicate).forEach { it.addPoints(points) }
        return points.values.count { it > 1 }
    }

    fun part1(input: List<String>) = dangerPoints(input) { !it.isDiagonal() }

    fun part2(input: List<String>) = dangerPoints(input)

    val testInput = readInput("day05/Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("day05/Day05")
    part1(input).println()
    part2(input).println()
}
