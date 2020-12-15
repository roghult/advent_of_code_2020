package days

import Day
import kotlin.math.ceil

class Day13: Day {
    override val puzzleInput = "input_day_13.txt"
    override val puzzleTestInput = "test_input_day_13.txt"

    override fun task1(input: List<String>): String {
        val depart = input.first().toInt()
        val bussTimes = input[1].split(",").filterNot { it == "x" }.map {
            val time = it.toInt()
            val foo = ceil(depart / time.toDouble())
            val bar = time * foo
            val diff = bar - depart
            time to diff
        }
        val bestBuss = bussTimes.minByOrNull { it.second }!!
        val answer = bestBuss.first * bestBuss.second
        return answer.toString()
    }

    override fun task2(input: List<String>): String {
        val bussTimes = input[1].split(",").mapIndexedNotNull { index, s ->
            if (s != "x") s.toLong() to index else null
        }

        val maxByOrNull = bussTimes.maxByOrNull { it.first }!!
        val changeValue = maxByOrNull.first - maxByOrNull.second
        var seekedValue = 0L
        var keepLooking = true
        while (keepLooking) {
            seekedValue += changeValue
            val res = bussTimes.filter {
                val l = (seekedValue + it.second) % it.first
                l != 0L
            }
            keepLooking  = res.isNotEmpty()
        }



        return seekedValue.toString()
    }
}
