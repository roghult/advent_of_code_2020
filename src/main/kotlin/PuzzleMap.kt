interface PuzzleMap {
}

enum class PuzzleTile {
    EMPTY,
    TREE,
}

class ExtendingPuzzleMap(
    val height: Int,
    val width: Int,
    private val puzzleMap: Map<Pair<Int, Int>, PuzzleTile>
): PuzzleMap {

    fun tileAt(coordinate: Pair<Int, Int>): PuzzleTile {
        val modifiedX = coordinate.first % width
        return puzzleMap.getValue(Pair(modifiedX, coordinate.second))
    }

    companion object {
        fun from(mapString: List<String>): ExtendingPuzzleMap {
            val width = mapString[0].length
            val puzzleMap = mapString.mapIndexed { yIndex, row ->
                row.mapIndexed { xIndex, c ->
                    val coordinate = Pair(xIndex, yIndex)
                    when (c) {
                        '#' -> {
                            coordinate to PuzzleTile.TREE
                        }
                        '.' -> {
                            coordinate to PuzzleTile.EMPTY
                        }
                        else -> TODO()
                    }
                }
            }.flatten().toMap()
            return ExtendingPuzzleMap(
                height = mapString.size,
                width = width,
                puzzleMap = puzzleMap
            )
        }
    }
}
