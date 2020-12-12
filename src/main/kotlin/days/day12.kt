package days

import Day
import kotlin.math.absoluteValue

enum class Direction(val value: Int) {
    NORTH(0),
    EAST(1),
    SOUTH(2),
    WEST(3);

    fun turn(degrees: Int): Direction {
        val amountTurns = degrees / 90
        val newValue = Math.floorMod((value + amountTurns), 4)
        return values().single { it.value == newValue }
    }
}

enum class Action {
    N,
    S,
    E,
    W,
    L,
    R,
    F;
}

class Day12 : Day {
    override val puzzleInput = "input_day_12.txt"
    override val puzzleTestInput = "test_input_day_12.txt"

    override fun task1(input: List<String>): String {
        val boat = Boat()
        input.forEach {
            val actionStr = it[0]
            val value = it.substring(1).toInt()
            val action = Action.valueOf(actionStr.toString())
            boat.move(action, value)
        }
        val answer = boat.manhattanFromBeginning()
        return answer.toString()
    }

    override fun task2(input: List<String>): String {
        return ""
    }
}

private class Boat(
        private var x: Int = 0,
        private var y: Int = 0,
        private var direction: Direction = Direction.EAST,
) {
    fun manhattanFromBeginning(): Int {
        return x.absoluteValue + y.absoluteValue
    }

    fun move(action: Action, value: Int) {
        when (action) {
            Action.N -> y += value
            Action.S -> y -= value
            Action.E -> x += value
            Action.W -> x -= value
            Action.L -> direction = direction.turn(-value)
            Action.R -> direction = direction.turn(value)
            Action.F -> {
                when (direction) {
                    Direction.NORTH -> y += value
                    Direction.EAST -> x += value
                    Direction.SOUTH -> y -= value
                    Direction.WEST -> x -= value
                }
            }
        }
    }
}
