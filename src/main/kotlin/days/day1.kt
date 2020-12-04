package days
import Day
import com.github.shiguruikai.combinatoricskt.combinations

class Day1: Day {
    override val puzzleInput = "input_day_1.txt"
    override val puzzleTestInput = "test_input_day_1.txt"

    override fun task1(input: List<String>): String {
        val combinations = input.combinations(2).map {
            it[0].toInt() to it[1].toInt()
        }
        val matching2020 = combinations.filter {
            it.first + it.second == 2020
        }.single()
        return (matching2020.first * matching2020.second).toString()
    }

    override fun task2(input: List<String>): String {
        val combinations = input.combinations(3).map {
            Triple(it[0].toInt(), it[1].toInt(), it[2].toInt())
        }
        val matching2020 = combinations.filter {
            it.first + it.second + it.third == 2020
        }.single()
        return (matching2020.first * matching2020.second * matching2020.third).toString()
    }

}
