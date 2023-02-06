package day16

import day16.Operation.*
import println
import readInput
import readInputAsText

enum class Operation(val id: String) { SUM("000"), PRODUCT("001"), MIN("010"), MAX("011"), LIT("100"), GT("101"), LT("110"), EQ("111")}
sealed interface Packet { val version: Int; val length: Int }
data class OperationPacket(override val version: Int, override val length: Int, val operation: Operation, val packets: List<Packet>) : Packet
data class LiteralPacket(override val version: Int, override val length: Int, val value: Long) : Packet

fun String.toBinaryString() = this.map { Integer.decode("0x${it}").toString(2).padStart(4, '0') }
    .joinToString("")

fun String.readPackets(maxPackets: Int? = null): List<Packet> {
    var remaining = this;
    val packets = mutableListOf<Packet>()
    while ((maxPackets == null || packets.size < maxPackets) && !remaining.matches("^0*$".toRegex())) {
        val packet = remaining.readPacket()
        packets.add(packet)
        remaining = remaining.substring(packet.length)
    }
    return packets
}

fun String.readPacket() = operation().let { if (it == LIT) this.readLiteralPacket() else this.readOperationPacket() }

fun String.readOperationPacket(): Packet {
    return if (this[6] == '0') {
        val packetsLength = toDecimal(7, 22)
        val packets = substring(22, 22 + packetsLength).readPackets()
        OperationPacket(version(), 22 + packetsLength, operation(), packets)
    } else {
        val packets = substring(18).readPackets(toDecimal(7, 18))
        OperationPacket(version(), 18 + packets.sumOf { it.length }, operation(), packets)
    }
}

fun String.readLiteralPacket(): LiteralPacket {
    val windowed = subSequence(6, length).windowed(5, 5)
    val groups = windowed.indexOfFirst { it.startsWith('0') } + 1
    val value = windowed.take(groups).joinToString("") { it.substring(1) }.toLong(2)
    return LiteralPacket(version(), 6 + 5 * groups, value)
}

fun String.toDecimal(startInd: Int, endInd: Int):Int = Integer.valueOf(substring(startInd, endInd), 2)
fun String.version() = substring(0, 3).toInt(2)
fun String.operation() = values().first { it.id == substring(3, 6) }

fun sumOfVersions(packet: Packet): Int = when (packet) {
    is LiteralPacket -> packet.version
    is OperationPacket -> packet.version + packet.packets.sumOf { sumOfVersions(it) }
}

fun getValue(packet: Packet): Long = when (packet) {
    is LiteralPacket -> packet.value
    is OperationPacket -> when (packet.operation) {
        SUM -> packet.packets.sumOf { getValue(it) }
        PRODUCT -> packet.packets.map { getValue(it) }.reduce(Long::times)
        MIN -> packet.packets.minOf { getValue(it) }
        MAX -> packet.packets.maxOf { getValue(it) }
        LIT -> getValue(packet)
        GT -> if (getValue(packet.packets[0]) > getValue(packet.packets[1])) 1 else 0
        LT -> if (getValue(packet.packets[0]) < getValue(packet.packets[1])) 1 else 0
        EQ -> if (getValue(packet.packets[0]) == getValue(packet.packets[1])) 1 else 0
    }
}

fun part1(input: String): Int {
    return input.toBinaryString().readPackets().sumOf { sumOfVersions(it) };
}

fun part2(input: String): Long {
    return input.toBinaryString().readPackets().sumOf { getValue(it) }
}

fun main() {
    check(readInput("day16/input_test_part1").map { part1(it) } == listOf(16, 12, 23, 31))
    check(readInput("day16/input_test_part2").map { part2(it) } == listOf(3L, 54L, 7L, 9L, 1L, 0L, 0L, 1L))

    val input = readInputAsText("day16/input")
    part1(input).println()
    part2(input).println()
}
