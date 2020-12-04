package days

import Day
import PassportVerification

class Day4: Day {
    override val puzzleInput = "input_day_4.txt"
    override val puzzleTestInput = "test_input_day_4.txt"

    override fun task1(input: List<String>): String {
        val passportStrings = parseInput(input)
        val requiredFields = setOf(
            "byr",
            "iyr",
            "eyr",
            "hgt",
            "hcl",
            "ecl",
            "pid",
//            "cid",
        )
        val missingCid = passportStrings.count {
            requiredFields.all { requiredField -> it.contains(requiredField) }
        }
        return missingCid.toString()
    }

    override fun task2(input: List<String>): String {
        val passportStrings = parseInput(input)
        val validator = PassportVerification()
        val validPassports = validator.validPassports(passportStrings)
        return validPassports.size.toString()
    }

    private fun parseInput(input: List<String>): List<String> {
        var fullPassportString = ""
        val fullPassportStrings = mutableListOf<String>()
        input.forEach { each ->
            if (each.isBlank()) {
                fullPassportStrings.add(fullPassportString.trim())
                fullPassportString = ""
            } else {
                fullPassportString += "$each "
            }
        }
        fullPassportStrings.add(fullPassportString.trim())
        return fullPassportStrings
    }
}
