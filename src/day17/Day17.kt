package day17

import println
import readInputAsText
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

data class Target(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int)

private fun String.parseTarget() = "target area: x=([-\\d]+)..([-\\d]+), y=([-\\d]+)..([-\\d]+)"
    .toRegex().matchEntire(this)!!
    .groupValues
    .drop(1)
    .map { it.toInt() }
    .let { Target(it[0], it[1], it[2], it[3]) }

/*
    If we look only at the y trajectory then it has to pass through 0 as it comes down
    since it is symmetrical. The same increments going up happen in reverse going down.

    The downward path is a series of triangular numbers and the problem consists in
    maximizing these.

    The largest possible jump is from 0 to the min y value in the target.

    The upward path is the same series of triangular numbers without this last jump.
 */
private fun Target.maxHeightPossible(): Int {
    val initialY = -minY - 1
    return initialY * (initialY + 1) / 2
}

/*
    minX = n(n-1)/2 where n is the initial X velocity
 */
private fun Target.minXVelocity() = ceil((-1 + sqrt(1.0 + 8 * minX)) / 2).toInt()

private fun Target.trajectoryHits(initialXV: Int, initialYV: Int): Boolean {
    var xV = initialXV
    var yV = initialYV
    var x = xV
    var y = yV
    while (y > minY && !isHit(x, y)) {
        yV--
        xV = (xV - 1).coerceAtLeast(0)
        x += xV
        y += yV
    }
    return isHit(x, y)
}

private fun Target.isHit(x: Int, y: Int) = x in minX..maxX && y in minY..maxY

private fun Target.maxYVelocity() = floor((-1 + sqrt(1 + 8 * maxHeightPossible().toFloat())) / 2).toInt()

fun part1(input: String): Int {
    return input.parseTarget().maxHeightPossible()
}

fun part2(input: String): Int {
    val target = input.parseTarget()
    return (target.minXVelocity()..target.maxX)
        .flatMap { xV -> (target.minY..target.maxYVelocity()).map { yV -> Pair(xV, yV) } }
        .count { (xV, yV) -> target.trajectoryHits(xV, yV) }
}

fun main() {
    check(part1(readInputAsText("day17/input_test")) == 45)
    check(part2(readInputAsText("day17/input_test")) == 112)

    val input = readInputAsText("day17/input")
    part1(input).println()
    part2(input).println()
}
