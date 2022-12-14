fun main() {

    fun readSignal(input: List<String>) = input.filter { line -> line.trim().isNotEmpty() }
        .map(String::toInt)

    fun countIncreases(readSignal: List<Int>) = readSignal
        .windowed(2)
        .fold(0) { acc, next -> if (next[1] > next[0]) acc + 1 else acc }

    fun part1(input: List<String>): Int {
        return countIncreases(readSignal(input))
    }

    fun part2(input: List<String>): Int {
        return countIncreases(readSignal(input).windowed(3).map { it -> it.sum() })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
