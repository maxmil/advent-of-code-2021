package day13

import println
import readInputAsText

data class Position(val x: Int, val y: Int)
data class Fold(val axis: Char, val position: Int)
typealias Paper = Set<Position>

fun String.parse(): Pair<Paper, List<Fold>> {
    val (coords, foldLines) = split("\n\n")
    val paper = coords.split("\n")
        .map { line -> line.split(",").let { Position(it.first().toInt(), it.last().toInt()) } }
        .toSet()
    val folds = foldLines.split("\n")
        .map { foldLine ->
            foldLine.split("=").let {
                Fold(
                    it.first().split(" ").last().first(),
                    it.last().toInt()
                )
            }
        }
    return Pair(paper, folds)
}

fun Paper.fold(fold: Fold) = if (fold.axis == 'y') {
    map { if (it.y < fold.position) it else it.copy(y = 2 * fold.position - it.y) }.toSet()
} else {
    map { if (it.x < fold.position) it else it.copy(x = 2 * fold.position - it.x) }.toSet()
}

fun part1(input: String): Int {
    val (paper, folds) = input.parse()
    return paper.fold(folds[0]).size
}

fun part2(input: String) {
    var (paper, folds) = input.parse()
    folds.forEach { paper = paper.fold(it) }
    println()
    for (y in 0..paper.maxOf { it.y }) {
        for (x in 0..paper.maxOf { it.x }) {
            print(if (paper.contains(Position(x, y))) "#" else " ")
        }
        println()
    }
    println()
}

fun main() {

    val testInput = readInputAsText("day13/input_test")
    check(part1(testInput) == 17)
    part2(testInput)

    val input = readInputAsText("day13/input")
    part1(input).println()
    part2(input)
}