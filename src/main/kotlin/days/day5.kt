package days

import Day

class Day5: Day {
    override val puzzleInput = "input_day_5.txt"
    override val puzzleTestInput = "test_input_day_5.txt"

    override fun task1(input: List<String>): String {
        val seats = input.map { findSeat(it, 1, 128) }
        return seats.maxOf { it.seatId() }.toString()
    }

    override fun task2(input: List<String>): String {
        val seats = input.map { findSeat(it, 1, 128) }.map { it.seatId() }.toSet()
        val expectedSeats = (1..127).map { row ->
            (0..7).map { column ->
                Seat(row, column).seatId()
            }
        }.flatten().toSet()
        val missingSeats = expectedSeats - seats
        val mySeat = missingSeats.single { missingSeat ->
            seats.contains(missingSeat + 1) && seats.contains(missingSeat - 1)
        }
        return mySeat.toString()
    }
}

data class Seat(
    val row: Int,
    val column: Int
) {
    fun seatId() = (row * 8) + column

    override fun toString(): String {
        return "row $row, column $column, seat ID ${seatId()}"
    }
}

fun findSeat(seatSpecification: String, minRows: Int, maxRows: Int): Seat {
    var minRow = minRows
    var maxRow = maxRows
    seatSpecification.take(7).forEach { each ->
        val newVals = binaryFoo(each, minRow, maxRow)
        minRow = newVals.first
        maxRow = newVals.second
    }
    require(minRow == maxRow)

    var minColumn = 1
    var maxColumn = 8
    seatSpecification.takeLast(3).forEach { each ->
        val newVals = binaryFoo(each, minColumn, maxColumn)
        minColumn = newVals.first
        maxColumn = newVals.second
    }
    require(minColumn == maxColumn)

    return Seat(
        row = minRow - 1,
        column = minColumn - 1,
    )
}

val up = setOf('B', 'R')
val down = setOf('F', 'L')

fun binaryFoo(upOrDown: Char, min: Int, max: Int): Pair<Int, Int> {
    // TODO need this?
    if (max - min == 1) {
        return when {
            up.contains(upOrDown) -> Pair(max, max)
            down.contains(upOrDown) -> Pair(min, min)
            else -> TODO()
        }
    }

    // 1 - 128
    // U: 65 - 128
    // D: 1 - 64
    val middle = (max - min) / 2
    return when {
        up.contains(upOrDown) -> Pair(min + middle + 1, max)
        down.contains(upOrDown) -> Pair(min, min + middle)
        else -> TODO()
    }
}
