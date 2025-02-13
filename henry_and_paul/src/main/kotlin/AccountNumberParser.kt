package org.yorkdevelopers

class AccountNumberParser {
    fun parseChar(input: String): String {
        return when (input) {
            "   \n  |\n  |" -> "1"
            " _ \n _|\n|_ " -> "2"
            " _ \n _|\n _|" -> "3"
            "   \n|_|\n  |" -> "4"
            " _ \n|_ \n _|" -> "5"
            " _ \n|_ \n|_|" -> "6"
            " _ \n  |\n  |" -> "7"
            " _ \n|_|\n|_|" -> "8"
            " _ \n|_|\n _|" -> "9"
            " _ \n| |\n|_|" -> "0"
            else -> "?"
        }
    }

    fun parseAccountNumber(input: String): String {
        val size = input.indexOfFirst { it == '\n' } / 3
        return input
            .filterNot { it == '\n' }
            .chunked(3)
            .foldIndexed(MutableList(size) { "" }) { index, acc, chunk ->
                acc[index % acc.size] = "${acc[index % acc.size]}$chunk\n"
                acc
            }
            .map { parseChar(it.trimEnd('\n')) }
            .reduce(String::plus)
    }
}

object AccountNumberValidator {
    fun validate(accountNumber: String): Boolean {
        val foldRightIndexed = accountNumber.reversed().foldRightIndexed(0) { index, char, acc ->
            acc + (index + 1) * char.digitToInt()
        }
        return foldRightIndexed % 11 == 0
    }
}

object AccountNumberProcessor {
    fun process(accountNumber: String): String {
        val parsedAccount = AccountNumberParser().parseAccountNumber(accountNumber)

        return if (parsedAccount.contains("?")) {
            "$parsedAccount ILL"
        } else if (!AccountNumberValidator.validate(parsedAccount)) {
            "$parsedAccount ERR"
        } else {
            parsedAccount
        }
    }
}
