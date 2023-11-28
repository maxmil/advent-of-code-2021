package day20

import println
import readInput

fun String.value() = map { c -> if (c == '#') "1" else "0" }.joinToString("").toInt(2)
fun enhance(image: List<String>, enhancement: String): List<String> =
    (1..image.size - 2).map { y ->
        (1..image[0].length - 2).map { x ->
            val square = image[y - 1].substring(x - 1..x + 1) +
                    image[y].substring(x - 1..x + 1) +
                    image[y + 1].substring(x - 1..x + 1)
            enhancement[square.value()]
        }.joinToString("")
    }

fun padImage(baseImage: List<String>, infinite: String): List<String> {
    val blankLine = infinite.repeat(baseImage[0].length)
    return (listOf(blankLine, blankLine) + baseImage + listOf(blankLine, blankLine)).map { row ->
        infinite.repeat(2) + row + infinite.repeat(2)
    }
}

fun repeatedEnhance(input: List<String>, times: Int): List<String> {
    val enhancement = input[0]
    var image = input.drop(2)
    var infinite = "."
    repeat(times) {
        image = enhance(padImage(image, infinite), enhancement)
        infinite = enhancement[infinite.repeat(9).value()].toString()
    }
    return image
}

fun List<String>.countLight() = fold(0) { acc, s -> acc + s.count { it == '#' } }
fun part1(input: List<String>) = repeatedEnhance(input, 2).countLight()
fun part2(input: List<String>) = repeatedEnhance(input, 50).countLight()
fun main() {
    check(part1(readInput("day20/input_test")) == 35)
    check(part2(readInput("day20/input_test")) == 3351)

    val input = readInput("day20/input")
    part1(input).println()
    part2(input).println()
}
