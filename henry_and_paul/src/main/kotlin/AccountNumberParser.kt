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
            else -> throw IllegalArgumentException("Unknown input \n$input")
        }
    }

    fun parseAccountNumber(input: String): String {
        val size = input.indexOfFirst{ it == '\n' } / 3
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
