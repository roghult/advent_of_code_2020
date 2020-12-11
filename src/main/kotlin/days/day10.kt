package days

import Day

class Day10: Day {
    override val puzzleInput = "input_day_10.txt"
    override val puzzleTestInput = "test_input_day_10.txt"
    private val maxDiff = 3

    override fun task1(input: List<String>): String {
        val values = input.map { it.toInt() }.toMutableList()
        values.add(0)
        val pathWithDifferences = joltToDifferenceWithNext(values, 0, emptyList())

        val foo = pathWithDifferences.groupBy { it.second }
        val answer = foo.getValue(1).size * foo.getValue(3).size
        return answer.toString()
    }

    private tailrec fun joltToDifferenceWithNext(values: List<Int>, currentValue: Int, path: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
        if (values.size == 1) {
            val value = values.first()
            return path + (currentValue to value - currentValue) + (value to 3)
        }

        val nextEligibleValue = values.filter { it - currentValue <= maxDiff }.minOrNull()!!
        val newPath = path + (currentValue to nextEligibleValue - currentValue)
        return joltToDifferenceWithNext(values.filter { it != nextEligibleValue }, nextEligibleValue, newPath)
    }

    override fun task2(input: List<String>): String {
        val values = input.map { it.toInt() }.toMutableList()
        val device = values.maxOrNull()!! + 3
        val pathWithDifferences = joltToDifferenceWithNext(values, 0, emptyList()) + (device to 3)
        val split = splitByDifferencesOf3(pathWithDifferences.sortedBy { it.first })
        val permutations = split.map {
            permutations(it.map { value -> value.first })
        }

        val answer = permutations.map { it.size.toLong() }.reduce { acc, mutableSet -> acc * mutableSet }
        return answer.toString()
    }

    private fun splitByDifferencesOf3(values: List<Pair<Int, Int>>): List<List<Pair<Int, Int>>> {
        val chainOfOnes = mutableListOf<Pair<Int, Int>>()
        val split = mutableListOf<List<Pair<Int, Int>>>()
        values.forEach{ value ->
            if (chainOfOnes.isEmpty()) {
                chainOfOnes.add(value)
            } else if (value.second == 1) {
                chainOfOnes.add(value)
            } else {
                if (chainOfOnes.isNotEmpty()) {
                    chainOfOnes.add(value)
                    split.add(chainOfOnes.toList())
                    chainOfOnes.clear()
                    chainOfOnes.add(value)
                }
            }
        }
        return split.filterNot { it.all { inner -> inner.second == 3 } }
    }

    private fun permutations(values: List<Int>): MutableSet<List<Int>> {
        val mustConnectToLower = values.first()
        val mustConnectToUpper = values.last()
        val validPaths = mutableSetOf(values)
        var previousSize = -1
        while (validPaths.size != previousSize) {
            previousSize = validPaths.size

            val newValidPaths = validPaths.toMutableSet()
            validPaths.forEach { validPath ->
                for (index in (1..(validPath.size - 2))) {
                    val newPath = validPath.toMutableList()
                    newPath.removeAt(index)
                    val validPath1 = isValidPath(newPath, mustConnectToLower, mustConnectToUpper)
                    if (validPath1) {
                        newValidPaths.add(newPath)
                    }
                }
            }
            validPaths.addAll(newValidPaths)
        }

        return validPaths
    }

    private fun isValidPath(values: List<Int>, mustConnectToLower: Int, mustConnectToUpper: Int): Boolean {
        if (values.first() - mustConnectToLower > 3) {
            return false
        }
        if (mustConnectToUpper - values.last() > 3) {
            return false
        }
        values.forEachIndexed { index, value ->
            if (index == values.size - 1) {
                return true
            }
            val nextValue = values[index + 1]
            val diff = nextValue - value
            if (diff > maxDiff) {
                return false
            }
        }
        return false
    }
}
