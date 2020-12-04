class PassportVerification {
    fun validPassports(passports: List<String>): List<Passport> {
        val passports = Passport.from(passports)
        val validPassports = passports
            .asSequence()
            .filter(this::isBirthYearValid)
            .filter(this::isIssueYearValid)
            .filter(this::isExpirationYearValid)
            .filter(this::isHeightValid)
            .filter(this::isHairColorValid)
            .filter(this::isEyeColorValid)
            .filter(this::isPassportIdValid)
            .toList()

        return validPassports
    }

    private fun isBirthYearValid(passport: Passport): Boolean {
        return passport.birthYear.isBetween(1920..2002) && passport.birthYear.hasLength(4)
    }

    private fun isIssueYearValid(passport: Passport): Boolean {
        return passport.issueYear.isBetween(2010..2020) && passport.issueYear.hasLength(4)
    }

    private fun isExpirationYearValid(passport: Passport): Boolean {
        return passport.expirationYear.isBetween(2020..2030) && passport.expirationYear.hasLength(4)
    }

    private fun isHeightValid(passport: Passport): Boolean {
        val value = passport.height ?: return false
        return when {
            value.contains("cm") -> {
                value.split("cm").first().isBetween(150..193)
            }
            value.contains("in") -> {
                value.split("in").first().isBetween(59..76)
            }
            else -> {
                return false
            }
        }
    }

    private fun isHairColorValid(passport: Passport): Boolean {
        val hairColor = passport.hairColor ?: return false
        val regex = Regex("^[a-f0-9]*$")
        return hairColor.hasLength(7) && hairColor[0] == '#' && regex.matches(hairColor.drop(1))
    }

    private fun isEyeColorValid(passport: Passport): Boolean {
        val eyeColor = passport.eyeColor ?: return false
        val allowedValues = setOf(
            "amb",
            "blu",
            "brn",
            "gry",
            "grn",
            "hzl",
            "oth"
        )
        return allowedValues.contains(eyeColor)
    }

    private fun isPassportIdValid(passport: Passport): Boolean {
        val passportId = passport.passportId ?: return false
        val regex = Regex("^[0-9]*$")
        return passportId.hasLength(9) && regex.matches(passportId)
    }
}

private fun String?.isBetween(range: IntRange) = this?.toInt() in range

private fun String?.hasLength(length: Int) = this?.length == length

data class Passport(
    val birthYear: String?,
    val issueYear: String?,
    val expirationYear: String?,
    val height: String?,
    val hairColor: String?,
    val eyeColor: String?,
    val passportId: String?,
    val countryId: String?,
) {
    companion object {
        fun from(passportStrings: List<String>): List<Passport> {
            val passportValues = passportStrings.map { passport ->
                passport.split(" ").associate {
                    val (key, value) = it.split(":")
                    key to value
                }
            }
            return passportValues.map { passportValue ->
                Passport(
                    birthYear = passportValue["byr"],
                    issueYear = passportValue["iyr"],
                    expirationYear = passportValue["eyr"],
                    height = passportValue["hgt"],
                    hairColor = passportValue["hcl"],
                    eyeColor = passportValue["ecl"],
                    passportId = passportValue["pid"],
                    countryId = passportValue["cid"],
                )
            }
        }
    }
}
