package day04

import println
import readInputAsText

typealias Board = List<List<BingoNumber>>

data class BingoNumber(val number: Int, var called: Boolean = false)
data class CompletedBoard(val number: Int, val board: Board)

fun main() {

    fun parseNumbers(input: String) = input.split("\n").first().split(",").map { it.toInt() }

    fun parseBoards(input: String) = input.split("\n\n").drop(1).map {
        it.split("\n")
            .map { row -> row.trim().split(Regex("\\s+")).map { col -> BingoNumber(col.toInt()) } }

    }

    fun play(numbers: List<Int>, boards: List<Board>): MutableList<CompletedBoard> {
        val completedBoards = mutableListOf<CompletedBoard>()
        numbers.forEach { number ->
            boards.forEach { board ->
                if (!completedBoards.map { it.board }.contains(board)) {
                    board.forEach { row ->
                        val foundIndex = row.indexOf(BingoNumber(number, false))
                        if (foundIndex != -1) {
                            row[foundIndex].called = true
                            if (row.none { !it.called } || board.map { it[foundIndex] }.none { !it.called }) {
                                completedBoards.add(CompletedBoard(number, board))
                            }
                        }
                    }
                }
            }
        }
        return completedBoards;
    }

    fun CompletedBoard.score() =
        this.number * this.board.sumOf { numbers -> numbers.filter { !it.called }.sumOf { it.number } }

    fun part1(input: String): Int {
        val numbers = parseNumbers(input)
        val boards = parseBoards(input)
        return play(numbers, boards).first().score()
    }

    fun part2(input: String): Int {
        val numbers = parseNumbers(input)
        val boards = parseBoards(input)
        return play(numbers, boards).last().score()
    }

    val testInput = readInputAsText("day04/Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInputAsText("day04/Day04")
    part1(input).println()
    part2(input).println()
}
