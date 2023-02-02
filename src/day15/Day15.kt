package day15

import println
import readInput
import java.util.Comparator
import java.util.PriorityQueue
import kotlin.system.measureTimeMillis

data class Point(val x: Int, val y: Int)
data class Node(val point: Point, val risk: Int, val distance: Int)

private fun List<String>.parse(tiles: Int, width: Int, height: Int) = (0..24).flatMap { tile ->
    val tileX = tile % tiles * width
    val tileY = tile / tiles * height
    flatMapIndexed { y, row ->
        row.mapIndexed { x, s ->
            val risk = ((s.toString().toInt() + tile % tiles + tile / tiles)).let { if (it > 9) it % 9 else it }
            val shortestPath = if (tileX + x == 0 && tileY + y == 0) 0 else Int.MAX_VALUE
            Node(Point(tileX + x, tileY + y), risk, shortestPath)
        }
    }
}.associateBy { it.point }

private fun findPathWithLowestRisk(input: List<String>, tiles: Int): Int {
    val height = input.size
    val width = input[0].length
    val maxY = height * tiles - 1
    val maxX = width * tiles - 1
    val grid = input.parse(tiles, width, height).toMutableMap()
    val queue = PriorityQueue<Node>(Comparator.comparing { node -> node.distance })
    val visited = mutableSetOf<Node>()

    queue.add(grid[Point(0, 0)]!!)
    while (queue.isNotEmpty()) {
        val node = queue.poll()
        arrayListOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))
            .map { Point(it.x + node.point.x, it.y + node.point.y) }
            .forEach {
                grid[it]?.apply {
                    if (!visited.contains(this) && this.distance > node.distance + risk) {
                        val newNode = copy(distance = node.distance + risk)
                        queue.remove(this)
                        queue.add(newNode)
                        grid[this.point] = newNode
                    }
                }
            }
        visited.add(node)
    }
    return grid[Point(maxX, maxY)]!!.distance
}

fun part1(input: List<String>): Int {
    return findPathWithLowestRisk(input, 1)
}

fun part2(input: List<String>): Int {
    return findPathWithLowestRisk(input, 5)
}

fun main() {
    val testInput = readInput("day15/input_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInput("day15/input")
    part1(input).println()
    measureTimeMillis { print(part2(input)) }.apply { println(" in $this ms") }
}
