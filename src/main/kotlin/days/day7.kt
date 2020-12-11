package days

import Day

class Day7: Day {
    override val puzzleInput = "input_day_7.txt"
    override val puzzleTestInput = "test_input_day_7.txt"
    private val sgb = "shiny gold"

    override fun task1(input: List<String>): String {
        val bagMap = inputToBagMap(input)

        val bagsLeadingToSGB = emptySet<String>().toMutableSet()
        var previousSize = -1
        while (previousSize != bagsLeadingToSGB.size) {
            previousSize = bagsLeadingToSGB.size

            val containingSGB = bagMap.filter { (_, containing) ->
                containing.any { it.second == sgb }
            }
            bagsLeadingToSGB.addAll(containingSGB.keys)

            val containingLink = bagMap.filter { (_, containing) ->
                containing.any { bagsLeadingToSGB.contains(it.second) }
            }
            bagsLeadingToSGB.addAll(containingLink.keys)
        }
        val answer = bagsLeadingToSGB.size.toString()
        require(answer == "112")
        return answer
    }

    override fun task2(input: List<String>): String {
        val bagMap = inputToBagMap(input)
        val bagsToCheck = mutableListOf(sgb)
        val bagsNeeded = mutableListOf<Pair<Int, String>>()
        while (bagsToCheck.isNotEmpty()) {
            val bagToCheck = bagsToCheck.removeAt(0)
            val bagContains = bagMap.getValue(bagToCheck)
            bagsNeeded.addAll(bagContains)
            bagContains.forEach { (amount, bag) ->
                repeat(amount) {
                    bagsToCheck.add(bag)
                }
            }
        }

        val answer = bagsNeeded.sumBy { it.first }
        require(answer == 6260)
        return answer.toString()
    }

    fun task2Recursive(input: List<String>): String {
        val bagMap = inputToBagMap(input)
        val bagsNeeded = countBags(listOf(sgb), bagMap, emptyList())
        val answer = bagsNeeded.sumBy { it.first }
        require(answer == 6260)
        return answer.toString()
    }

    private tailrec fun countBags(
        bagsToCheck: List<String>,
        bagMap: Map<String, List<Pair<Int, String>>>,
        bagContains: List<Pair<Int, String>>,
    ): List<Pair<Int, String>> {
        if (bagsToCheck.isEmpty()) {
            return bagContains
        }

        val bag = bagsToCheck[0]
        val newBagContains = bagMap.getValue(bag)
        val newBagsToCheck = bagsToCheck.drop(1) + newBagContains.map { (amount, b) ->
            List(amount) { b }
        }.flatten()

        return countBags(newBagsToCheck, bagMap, bagContains + newBagContains)
    }

    private fun inputToBagMap(input: List<String>): Map<String, List<Pair<Int, String>>> {
        val map = input.map { bagLine ->
            val bagAndContaining = bagLine.split(" contain ")
            val bag = bagName(bagAndContaining[0])
            val containingBags = bagAndContaining[1].split(", ").map { containingBagString ->
                if (containingBagString == "no other bags.") {
                    null
                } else {
                    val amount = containingBagString.filter { it.isDigit() }.toInt()
                    val containingBag = bagName(containingBagString)
                    amount to containingBag
                }
            }.filterNotNull()
            bag to containingBags
        }.toMap()
        return map
    }
    private fun bagName(string: String): String {
        return removeBagString(string.filter { it.isLetter() || it == ' ' }).trim()
    }

    private fun removeBagString(string: String): String {
        return string.replace(" bags", "").replace(" bag", "")
    }
}
