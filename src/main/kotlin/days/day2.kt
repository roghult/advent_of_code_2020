package days

import Day
import OfficialPasswordVerifier
import OldVerifier
import PasswordEntry

class Day2: Day {
    override val puzzleInput = "input_day_2.txt"
    override val puzzleTestInput = "test_input_day_2.txt"

    override fun task1(input: List<String>): String {
        val passwordEntries = parseInput(input)
        val invalidPasswords = OldVerifier().validPasswords(passwordEntries)
        return invalidPasswords.size.toString()
    }

    override fun task2(input: List<String>): String {
        val passwordEntries = parseInput(input)
        val invalidPasswords = OfficialPasswordVerifier().validPasswords(passwordEntries)
        return invalidPasswords.size.toString()
    }

    private fun parseInput(input: List<String>): List<PasswordEntry> {
        return input.map {
            val foo = it.split("-", " ", ": ")
            PasswordEntry(
                policy1 = foo[0].toInt(),
                policy2 = foo[1].toInt(),
                character = foo[2][0],
                password = foo[3],
            )
        }
    }
}
