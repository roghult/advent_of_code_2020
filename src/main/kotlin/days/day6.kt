package days

import Day

class Day6: Day {
    override val puzzleInput = "input_day_6.txt"
    override val puzzleTestInput = "test_input_day_6.txt"

    override fun task1(input: List<String>): String {
        val allGroupAnswers = parseInput(input)
        val numUniqueAnswers = allGroupAnswers.map {
            it.replace("\n", "").toSet().size
        }
        return numUniqueAnswers.sum().toString()
    }

    override fun task2(input: List<String>): String {
        val allGroupAnswers = parseInput(input)
        val numAllAnswered = allGroupAnswers.map { answer ->
            val groupAnswers = answer.split("\n").map { it.toSet() }
            groupAnswers.reduce { acc, set ->  acc intersect set}.size
        }
        return numAllAnswered.sum().toString()
    }

    private fun parseInput(input: List<String>): List<String> {
        var groupAnswers = ""
        val allGroupAnswers = mutableListOf<String>()
        input.forEach { each ->
            if (each.isBlank()) {
                allGroupAnswers.add(groupAnswers.trim())
                groupAnswers = ""
            } else {
                groupAnswers += "$each\n"
            }
        }
        allGroupAnswers.add(groupAnswers.trim())
        return allGroupAnswers
    }
}
