package day09

import println
import readInput
import kotlin.system.measureTimeMillis

data class Position(val x: Int, val y: Int)
typealias Grid = List<List<Int>>

fun main() {

    fun List<String>.parseGrid() = map { row -> row.map { it.toString().toInt() } }

    fun Grid.withinBounds(it: Position) = it.x >= 0 && it.x < this[0].size && it.y >= 0 && it.y < this.size

    fun Grid.adjacent(position: Position) = listOf(
        position.copy(x = position.x - 1),
        position.copy(x = position.x + 1),
        position.copy(y = position.y - 1),
        position.copy(y = position.y + 1),
    ).filter { withinBounds(it) }

    fun Grid.isLowPoint(position: Position, value: Int): Boolean {
        return !adjacent(position).any { this[it.y][it.x] <= value }
    }

    fun Grid.lowPoints() = flatMapIndexed { rowIndex, row ->
        List(row.size) { colIndex -> Position(colIndex, rowIndex) }
            .filter { position -> isLowPoint(position, this[position.y][position.x]) }
    }

    fun Grid.basinSize(position: Position, visited: MutableSet<Position>): Int {
        if (visited.contains(position)) return 0
        visited.add(position)
        return adjacent(position)
            .filter { this[it.y][it.x] != 9 }
            .sumOf { this@basinSize.basinSize(it, visited) } + 1
    }

    fun part1(grid: Grid) = grid.lowPoints().sumOf { grid[it.y][it.x] + 1 }

    fun part2(grid: Grid): Int {
        val visited = mutableSetOf<Position>()
        return grid.lowPoints()
            .map { grid.basinSize(it, visited) }
            .sorted()
            .takeLast(3)
            .reduce { acc, size -> acc * size }
    }

    fun part2BFS(grid: Grid): Int {
        return grid.lowPoints().map { lowPoint ->
            var cnt = 0
            val visited = mutableSetOf<Position>()
            val toVisit = grid.adjacent(lowPoint).toMutableList()
            while (toVisit.isNotEmpty()) {
                val position = toVisit.removeFirst()
                if (!visited.add(position) || grid[position.y][position.x] == 9) continue
                cnt++;
                toVisit.addAll(grid.adjacent(position))
            }
            cnt;
        }.sorted()
            .takeLast(3)
            .reduce { acc, size -> acc * size }
    }

    val testGrid = readInput("day09/input_test").parseGrid()
    check(part1(testGrid) == 15)
    check(part2(testGrid) == 1134)
    check(part2BFS(testGrid) == 1134)

    val grid = readInput("day09/input").parseGrid()
    part1(grid).println()
    measureTimeMillis {  print(part2(grid)) }.let { println(" in ${it}ms") }
    measureTimeMillis {  print(part2BFS(grid)) }.let { println(" in ${it}ms") }
}
