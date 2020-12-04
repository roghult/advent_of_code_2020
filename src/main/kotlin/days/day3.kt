package days

import Day
import ExtendingPuzzleMap
import PuzzleTile

class Day3: Day {
    override val puzzleInput = "input_day_3.txt"
    override val puzzleTestInput = "test_input_day_3.txt"

    override fun task1(input: List<String>): String {
        val puzzleMap = ExtendingPuzzleMap.from(input)
        val treesFound = treesInPath(
            xSteps = 3,
            ySteps = 1,
            puzzleMap,
        )

        return treesFound.toString()
    }

    override fun task2(input: List<String>): String {
        val puzzleMap = ExtendingPuzzleMap.from(input)
        val steps = listOf(
            Pair(1, 1),
            Pair(3, 1),
            Pair(5, 1),
            Pair(7, 1),
            Pair(1, 2),
        )
        val treesFound = steps.map {
            treesInPath(
                xSteps = it.first,
                ySteps = it.second,
                puzzleMap,
            )
        }

        return treesFound.map { it.toLong() }.reduce { acc, i -> acc * i }.toString()
    }

    private fun treesInPath(xSteps: Int, ySteps: Int, puzzleMap: ExtendingPuzzleMap): Int {
        var nextCoordinate = Pair(0, 0)
        var treesFound = 0
        while (nextCoordinate.second < puzzleMap.height) {
            val tile = puzzleMap.tileAt(nextCoordinate)
            treesFound += when (tile) {
                PuzzleTile.TREE -> 1
                PuzzleTile.EMPTY -> 0
            }
            nextCoordinate = Pair(nextCoordinate.first + xSteps, nextCoordinate.second + ySteps)
        }
        return treesFound
    }

}
