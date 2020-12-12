import days.*
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
    val day = Day12()
    val input = readInputLines(day.puzzleInput)
    val testInput = readInputLines(day.puzzleTestInput)
    println()
    println("Task 1 test answer: ${day.task1(testInput)}")
    println("Task 1 answer: ${day.task1(input)}")
    println()
    println("Task 2 test answer: ${day.task2(testInput)}")
    println("Task 2 answer: ${day.task2(input)}")
}
