package days

import Day

enum class Command {
    ACC,
    JMP,
    NOP,
}

class Day8: Day {
    override val puzzleInput = "input_day_8.txt"
    override val puzzleTestInput = "test_input_day_8.txt"

    override fun task1(input: List<String>): String {
        var accumulator = 0
        val executedLines = emptySet<Int>().toMutableSet()
        var linePointer = 0
        while (!executedLines.contains(linePointer)) {
            executedLines.add(linePointer)
            val (command, value) = parseLine(input[linePointer])
            linePointer += linePointerChange(command, value)
            accumulator += accumulatorChange(command, value)
        }
        require(listOf(5, 1671).contains(accumulator))
        return accumulator.toString()
    }

    override fun task2(input: List<String>): String {
        val toChange = input.mapIndexed { index, s -> index to s }
            .filter { it.second.contains("jmp") || it.second.contains("nop") }
            .toMutableList()
        var endReached = false
        var accumulator = 0
        while (!endReached) {
            accumulator = 0
            var linePointer = 0
            val executedLines = emptySet<Int>().toMutableSet()
            val changeValue = toChange.removeAt(0)
            val modifiedInput = input.mapIndexed { index, existing ->
                if (index == changeValue.first) {
                    if (changeValue.second.contains("jmp")) {
                        changeValue.second.replace("jmp", "nop")
                    } else {
                        changeValue.second.replace("nop", "jmp")
                    }
                } else existing
            }
            while (!executedLines.contains(linePointer) && !endReached) {
                executedLines.add(linePointer)
                val (command, value) = parseLine(modifiedInput[linePointer])
                linePointer += linePointerChange(command, value)
                accumulator += accumulatorChange(command, value)
                endReached = linePointer >= input.size
            }
        }
        require(listOf(8, 892).contains(accumulator))
        return accumulator.toString()
    }

    private fun linePointerChange(command: Command, value: Int): Int {
        return when (command) {
            Command.ACC,
            Command.NOP -> 1
            Command.JMP -> value
        }
    }

    private fun accumulatorChange(command: Command, value: Int): Int {
        return when (command) {
            Command.JMP,
            Command.NOP -> 0
            Command.ACC -> value
        }

    }

    private fun parseLine(line: String): Pair<Command, Int> {
        val commandAndValue = line.split(" ")
        val command = Command.valueOf(commandAndValue[0].toUpperCase())
        val value = commandAndValue[1].toInt()
        return command to value
    }
}
