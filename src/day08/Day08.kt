package day08

import println
import readInput

fun main() {

    fun part1(input: List<String>) =
        input.sumOf { it.split("|")[1].split(" ").count { word -> listOf(2, 3, 4, 7).contains(word.length) } }

    fun String.intersect(decoded: String?) = decoded!!.toCharArray().count { c -> contains(c) }

    fun List<String>.decode(decoded: Array<String?>, length: Int, charsIn1: Int, charsIn4: Int, charsIn7: Int) =
        first {
            it.length == length
                    && it.intersect(decoded[1]) == charsIn1
                    && it.intersect(decoded[4]) == charsIn4
                    && it.intersect(decoded[7]) == charsIn7
        }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            line.split(" | ").let { (before, after) ->
                val numbers = before.split(" ").map { String(it.toCharArray().sorted().toCharArray()) }
                val output = after.split(" ").map { String(it.toCharArray().sorted().toCharArray()) }
                val decoded = arrayOfNulls<String>(10)
                decoded[1] = numbers.first { it.length == 2 }
                decoded[4] = numbers.first { it.length == 4 }
                decoded[7] = numbers.first { it.length == 3 }
                decoded[8] = numbers.first { it.length == 7 }
                decoded[0] = numbers.decode(decoded, 6, 2, 3, 3)
                decoded[2] = numbers.decode(decoded, 5, 1, 2, 2)
                decoded[3] = numbers.decode(decoded, 5, 2, 3, 3)
                decoded[5] = numbers.decode(decoded, 5, 1, 3, 2)
                decoded[6] = numbers.decode(decoded, 6, 1, 3, 2)
                decoded[9] = numbers.decode(decoded, 6, 2, 4, 3)
                output.fold("") { acc, s -> acc + decoded.indexOf(s) }.toInt()
            }
        }
    }

    val testInput = readInput("day08/input_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("day08/input")
    part1(input).println()
    part2(input).println()
}
