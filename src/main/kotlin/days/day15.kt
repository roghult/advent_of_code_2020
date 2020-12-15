package days
import Day

class Day15: Day {
    override val puzzleInput = "input_day_15.txt"
    override val puzzleTestInput = "test_input_day_15.txt"

    override fun task1(input: List<String>): String {
        val numbers = input[0].split(",").map { it.toInt() }
        val answerUsingList = numberAfterIterationsUsingList(2020, numbers)
        val answerUsingMap = numberAfterIterationsUsingMap(2020, numbers)
        return answerUsingList.toString()
    }

    override fun task2(input: List<String>): String {
        val numbers = input[0].split(",").map { it.toInt() }
        val answer = numberAfterIterationsUsingMap(30000000, numbers)
        return answer.toString()
    }

    private fun numberAfterIterationsUsingMap(iterations: Int, startingNumbers: List<Int>): Int {
        val numberToIndex = startingNumbers.dropLast(1).mapIndexed { index, l ->
            l to index
        }.toMap().toMutableMap()
        var currentNumber = startingNumbers.last()

        ((startingNumbers.size - 1)..iterations).forEach { turn ->
            val previousIndexOfCurrentNumber = numberToIndex[currentNumber]
            val nextNumber = if (previousIndexOfCurrentNumber == null) {
                0
            } else {
                turn - previousIndexOfCurrentNumber
            }
            numberToIndex[currentNumber] = turn
            currentNumber = nextNumber
        }

        return numberToIndex.filterValues { it == iterations - 1}.keys.single()
    }

    private fun numberAfterIterationsUsingList(iterations: Int, startingNumbers: List<Int>): Int {
        val numbers = startingNumbers.toMutableList()

        ((numbers.size + 1)..iterations).forEach { turn ->
            val previousNumber = numbers[turn - 2]
            val latestIndexOfPreviousNumber = numbers.dropLast(1).lastIndexOf(previousNumber)
            val nextNumber = if (latestIndexOfPreviousNumber == -1) {
                0
            } else {
                (turn - 1) - (latestIndexOfPreviousNumber + 1)
            }
            numbers.add(nextNumber)
        }

        val answer = numbers.last()
        return answer
    }
}
