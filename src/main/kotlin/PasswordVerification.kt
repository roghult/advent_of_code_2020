data class PasswordEntry(
    val policy1: Int,
    val policy2: Int,
    val character: Char,
    val password: String,
)

interface PasswordVerification {
    fun validPasswords(passwordEntries: List<PasswordEntry>): List<PasswordEntry>
}

class OldVerifier: PasswordVerification {
    override fun validPasswords(passwordEntries: List<PasswordEntry>): List<PasswordEntry> {
        return passwordEntries.filter { passwordEntry ->
            val count = passwordEntry.password.count { it == passwordEntry.character }
            val minOccurrencesValid = count >= passwordEntry.policy1
            val maxOccurrencesValid = count <= passwordEntry.policy2
            minOccurrencesValid && maxOccurrencesValid
        }
    }
}

class OfficialPasswordVerifier: PasswordVerification {
    override fun validPasswords(passwordEntries: List<PasswordEntry>): List<PasswordEntry> {
        return passwordEntries.filter { passwordEntry ->
            val firstMatches = passwordEntry.password[passwordEntry.policy1 - 1] == passwordEntry.character
            val secondMatches = passwordEntry.password[passwordEntry.policy2 - 1] == passwordEntry.character
            !(firstMatches && secondMatches) && (firstMatches || secondMatches)
        }
    }

}
