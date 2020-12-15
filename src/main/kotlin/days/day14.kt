package days

import Day
import kotlin.math.pow

class Day14: Day {
    override val puzzleInput = "input_day_14.txt"
    override val puzzleTestInput = "test_input_day_14.txt"

    override fun task1(input: List<String>): String {
        val memory = mutableMapOf<String, String>()
        var mask = ""
        input.forEach { line ->
            val split = line.split(" = ")
            val command = split[0]
            val value = split[1]
            when (command) {
                "mask" -> mask = value
                else -> {
                    val binaryValue = value.toBinaryString()
                    val valueAfterMask = binaryValue.applyValueMask(mask)
                    memory[command] = valueAfterMask
                }
            }
        }
        val answer = memory.map { (key, value) ->
            value.binaryStringToLong()
        }.reduce { acc, i -> acc + i }
        return answer.toString()
    }

    override fun task2(input: List<String>): String {
        val memory = mutableMapOf<String, String>()
        var mask = ""
        input.forEach { line ->
            val split = line.split(" = ")
            val command = split[0]
            val value = split[1]
            when (command) {
                "mask" -> mask = value
                else -> {
                    val memoryAddress= command.replace("mem[", "").replace("]", "").toBinaryString()
                    val memoryAddressAfterMask = memoryAddress.applyMemoryMask(mask)
                    val binaryValue = value.toBinaryString()
                    val indexesWithX = mask.mapIndexedNotNull { index, c ->
                        if (c == 'X') index else null
                    }
                    val memoryAddresses = mutableListOf<String>()
                    var numCombinations = 2.0.pow(indexesWithX.size).toInt()
                    repeat(numCombinations) {
                        val sb = StringBuilder(memoryAddressAfterMask)
                        val binaryString = Integer.toBinaryString(it)
                        val repeat = indexesWithX.size - binaryString.length
                        val paddedBinaryString = "0".repeat(repeat) + binaryString
                        paddedBinaryString.forEachIndexed { index, c ->
                            val indexToChange = indexesWithX[index]
                            sb[indexToChange] = c
                        }
                        memoryAddresses.add(sb.toString())
                    }

                    memoryAddresses.forEach {
                        memory[it] = binaryValue
                    }
                }
            }
        }
        val answer = memory.map { (key, value) ->
            value.binaryStringToLong()
        }.reduce { acc, i -> acc + i }
        // 4197941339968
        return answer.toString()
    }
}

private fun String.binaryStringToLong(): Long {
    return this.toLong(2)
}

private fun String.applyValueMask(mask: String) =
        this.mapIndexed  { index, c ->
            when (mask[index]) {
                'X' -> c
                else -> mask[index]
            }
        }.joinToString("")

private fun String.applyMemoryMask(mask: String) =
        this.mapIndexed  { index, c ->
            when (mask[index]) {
                '0' -> c
                'X' -> 'X'
                '1' -> '1'
                else -> TODO()
            }
        }.joinToString("")

private fun String.toBinaryString(): String {
    val desiredLength = 36
    var binaryString = Integer.toBinaryString(toInt())
    val times = desiredLength - binaryString.length
    binaryString = "0".repeat(times) + binaryString
    return binaryString
}
