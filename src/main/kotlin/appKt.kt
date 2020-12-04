import days.Day3
import days.Day4
import java.io.File

interface Day {
    val puzzleInput: String
    val puzzleTestInput: String
    fun task1(input: List<String>): String
    fun task2(input: List<String>): String
}

fun readInput(fileName: String, dir: String = "src/main/resources/"): String {
    val fullPath = dir + fileName
    return File(fullPath).readText()
}

fun readInputLines(fileName: String, dir: String = "src/main/resources/"): List<String> {
    val fullPath = dir + fileName
    return File(fullPath).readLines()
}

fun main() {
    val day = Day4()
    val input = readInputLines(day.puzzleInput)
//    val input = readInputLines(day.puzzleTestInput)
    println()
    println(day.task1(input))
    println(day.task2(input))
}
