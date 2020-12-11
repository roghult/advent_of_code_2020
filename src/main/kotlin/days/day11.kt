package days

import Day

enum class SeatTile {
    FLOOR,
    EMPTY,
    OCCUPIED
}

class Day11: Day {
    override val puzzleInput = "input_day_11.txt"
    override val puzzleTestInput = "test_input_day_11.txt"

    private val adjacentCoordinates = listOf(
            0 to 1,
            1 to 1,
            1 to 0,
            1 to -1,
            0 to -1,
            -1 to -1,
            -1 to 0,
            -1 to 1,
    )

    override fun task1(input: List<String>): String {
        var currentSeatMap = seatMap(input)
        var nexSeatMap = currentSeatMap.toMap()
        var seatMapString = seatMapString(currentSeatMap)
        var newSeatMapString = seatMapString(nexSeatMap)
        do {
            currentSeatMap = nexSeatMap
            nexSeatMap = currentSeatMap.map { (coordinate, seatTile) ->
                val neighbours = adjacentCoordinates.mapNotNull { adjacentCoordinate ->
                    val neighbourX = coordinate.first + adjacentCoordinate.first
                    val neighbourY = coordinate.second + adjacentCoordinate.second
                    val neighbourCoordinate = neighbourX to neighbourY
                    currentSeatMap[neighbourCoordinate]
                }

                val occupiedNeighbours = neighbours.filter { it == SeatTile.OCCUPIED }

                val new = when {
                    seatTile == SeatTile.EMPTY && occupiedNeighbours.isEmpty() -> coordinate to SeatTile.OCCUPIED
                    seatTile == SeatTile.OCCUPIED && occupiedNeighbours.size >= 4 -> coordinate to SeatTile.EMPTY
                    else -> coordinate to seatTile
                }
                new
            }.toMap()
            seatMapString = seatMapString(currentSeatMap)
            newSeatMapString = seatMapString(nexSeatMap)
        } while (currentSeatMap != nexSeatMap)

        val answer = currentSeatMap.filterValues { it == SeatTile.OCCUPIED }.count()
        return answer.toString()
    }

    override fun task2(input: List<String>): String {
        var currentSeatMap = seatMap(input)
        var nexSeatMap = currentSeatMap.toMap()
        var seatMapString = seatMapString(currentSeatMap)
        var newSeatMapString = seatMapString(nexSeatMap)
        do {
            currentSeatMap = nexSeatMap
            nexSeatMap = currentSeatMap.map { (coordinate, seatTile) ->
                val updatedEntry = if (seatTile == SeatTile.FLOOR) {
                    coordinate to seatTile
                } else {
                    val visibleNeighbours = adjacentCoordinates.mapNotNull { adjacentCoordinate ->
                        var neighbourX = coordinate.first
                        var neighbourY = coordinate.second
                        var neighbourTile: SeatTile?

                        do {
                            neighbourX += adjacentCoordinate.first
                            neighbourY += adjacentCoordinate.second
                            val neighbourCoordinate = neighbourX to neighbourY
                            neighbourTile = currentSeatMap[neighbourCoordinate]
                        } while (neighbourTile != null && neighbourTile == SeatTile.FLOOR)

                        neighbourTile
                    }

                    val occupiedNeighbours = visibleNeighbours.filter { it == SeatTile.OCCUPIED }

                    val new = when {
                        seatTile == SeatTile.EMPTY && occupiedNeighbours.isEmpty() -> coordinate to SeatTile.OCCUPIED
                        seatTile == SeatTile.OCCUPIED && occupiedNeighbours.size >= 5 -> coordinate to SeatTile.EMPTY
                        else -> coordinate to seatTile
                    }
                    new
                }
                updatedEntry
            }.toMap()
            seatMapString = seatMapString(currentSeatMap)
            newSeatMapString = seatMapString(nexSeatMap)
        } while (currentSeatMap != nexSeatMap)

        val answer = currentSeatMap.filterValues { it == SeatTile.OCCUPIED }.count()
        return answer.toString()
    }

    fun seatMap(mapString: List<String>): Map<Pair<Int, Int>, SeatTile> {
        val puzzleMap = mapString.mapIndexed { yIndex, row ->
            row.mapIndexed { xIndex, c ->
                val coordinate = Pair(xIndex, yIndex)
                when (c) {
                    '#' -> coordinate to SeatTile.OCCUPIED
                    '.' -> coordinate to SeatTile.FLOOR
                    'L' -> coordinate to SeatTile.EMPTY
                    else -> TODO()
                }
            }
        }.flatten().toMap()
        return puzzleMap
    }

    fun seatMapString(seatMap: Map<Pair<Int, Int>, SeatTile>): String {
        if (seatMap.isEmpty()) return ""

        val width = seatMap.maxByOrNull { it.key.first }!!.key.first
        val height = seatMap.maxByOrNull { it.key.second }!!.key.second

        val str = (0..height).map { y ->
            (0..width).map { x ->
                when (seatMap.getValue(x to y)) {
                    SeatTile.FLOOR -> '.'
                    SeatTile.EMPTY -> 'L'
                    SeatTile.OCCUPIED -> '#'
                }
            }.joinToString("") + "\n"
        }.reduce { acc, s -> acc + s }
        return str
    }
}
