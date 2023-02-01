package day15

import println
import readInput
import kotlin.math.min
import kotlin.system.exitProcess
import kotlin.system.measureTimeMillis

data class Node(val x: Int, val y: Int, val risk: Int, var shortestPath: Int)

fun part1(input: List<String>): Int {
    return findPathWithLowestRisk(input, 1)
}

fun part2(input: List<String>): Int {
    return findPathWithLowestRisk(input, 5)
}

private fun findPathWithLowestRisk(input: List<String>, tiles: Int): Int {
    val height = input.size
    val width = input[0].length
    val maxY = height * tiles - 1
    val maxX = width * tiles - 1
    val unvisited = (0..24).flatMap { tile ->
        val tileX = tile % tiles * width
        val tileY = tile / tiles * height
        input.flatMapIndexed { y, row ->
            row.mapIndexed { x, s ->
                val risk = ((s.toString().toInt() + tile % tiles + tile / tiles)).let { if (it > 9) it % 9 else it }
                val shortestPath = if (tileX + x == 0 && tileY + y == 0) 0 else Int.MAX_VALUE - maxX - maxY
                Node(tileX + x, tileY + y, risk, shortestPath)
            }
        }
    }.groupBy { Pair(it.x, it.y) }.mapValues { it.value.first() }.toMutableMap()
    var curr = unvisited.values.minBy { it.shortestPath + (maxX - it.x) + (maxY - it.y) }
    while (curr.x != maxX || curr.y != maxY) {
        arrayListOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
            .map { Pair(it.first + curr.x, it.second + curr.y) }
            .forEach {
                unvisited[Pair(it.first, it.second)]?.apply {
                    this.shortestPath = min(this.shortestPath, curr.shortestPath + this.risk)
                }
            }

        unvisited.remove(Pair(curr.x, curr.y))
        curr = unvisited.values.minBy { it.shortestPath + (maxX - it.x) + (maxY - it.y) }
        println(unvisited.size)
    }
    println(curr)
    return curr.shortestPath
}

fun main() {
    val testInput = readInput("day15/input_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInput("day15/input")
    part1(input).println()
    measureTimeMillis { part2(input).println() }.apply { println("Duration $this") }
}
