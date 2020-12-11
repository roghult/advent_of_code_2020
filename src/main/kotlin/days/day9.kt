package days

import Day

class Day9: Day {
    override val puzzleInput = "input_day_9.txt"
    override val puzzleTestInput = "test_input_day_9.txt"

    override fun task1(input: List<String>): String {
        val preamble = 25
        val values = input.map { it.toLong() }
        values.forEachIndexed { index, value ->
            val validXmas = validXmas(values, index, preamble)
            if (!validXmas) {
                return value.toString()
            }
        }
        return ""
    }

    override fun task2(input: List<String>): String {
        val values = input.map { it.toLong() }
        val preamble = 25
        val firstInvalidValue = firstInvalidValue(values, preamble)
        val contiguousSet = contiguousSet(values, firstInvalidValue)
        val answer = contiguousSet.minOrNull()!! + contiguousSet.maxOrNull()!!
        return answer.toString()
    }

    private fun contiguousSet(values: List<Long>, sumValue: Long): List<Long> {
        values.forEachIndexed { index, value ->
            var sum = value
            val desiredValues = mutableListOf(value)
            var innerIndex = index
            var keepSearching = true
            while (keepSearching) {
                innerIndex++
                val innerValue = values[innerIndex]
                desiredValues.add(innerValue)
                sum += innerValue
                if (sum > sumValue) {
                    keepSearching = false
                }
                if (sum == sumValue) {
                    return desiredValues
                }
            }
        }
        TODO()
    }

    private fun firstInvalidValue(values: List<Long>, preamble: Int): Long {
        values.forEachIndexed { index, value ->
            val validXmas = validXmas(values, index, preamble)
            if (!validXmas) {
                return value
            }
        }
        TODO()
    }

    private fun validXmas(values: List<Long>, index: Int, preamble: Int): Boolean {
        if (index <= preamble) {
            return true
        }
        val indexValue = values[index]
        val range = (index - preamble - 1) until index
        range.forEach { inner ->
            range.forEach { outer ->
                if (inner != outer) {
                    if (values[inner] + values[outer] == indexValue) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
