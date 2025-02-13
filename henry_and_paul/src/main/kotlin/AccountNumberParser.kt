package org.yorkdevelopers

typealias CharResults = Pair<String, Array<String>>
class AccountNumberParser {

    fun parseChar(input: String) : String = parseCharResults(input.filterNot { it == '\n' }, false).first

    fun parseCharResults(input: String, recursed: Boolean = false): CharResults {
        return when (input) {
            "     |  |" -> CharResults("1", AccountNumberProcessor.convertNumbers("1"))
            " _  _||_ " -> CharResults("2", AccountNumberProcessor.convertNumbers("2"))
            " _  _| _|" -> CharResults("3", AccountNumberProcessor.convertNumbers("3"))
            "   |_|  |" -> CharResults("4", AccountNumberProcessor.convertNumbers("4"))
            " _ |_  _|" -> CharResults("5", AccountNumberProcessor.convertNumbers("5"))
            " _ |_ |_|" -> CharResults("6", AccountNumberProcessor.convertNumbers("6"))
            " _   |  |" -> CharResults("7", AccountNumberProcessor.convertNumbers("7"))
            " _ |_||_|" -> CharResults("8", AccountNumberProcessor.convertNumbers("8"))
            " _ |_| _|" -> CharResults("9", AccountNumberProcessor.convertNumbers("9"))
            " _ | ||_|" -> CharResults("0", AccountNumberProcessor.convertNumbers("0"))
            else -> {
                if (recursed) {
                    CharResults("?", arrayOf())
                } else {
                    val possibleAlternatives = mutableListOf<String>()
                    for (i in input.indices) {
                        for (j in 0 until 3) {
                            val newChar = when (j) {
                                0 -> '|'
                                1 -> '_'
                                2 -> ' '
                                else -> throw IllegalStateException("Invalid index")
                            }

                            val newInput = input.substring(0, i) + newChar + input.substring(i + 1)
                            val (value, _) = parseCharResults(newInput, true)
                            if (value != "?") {
                                possibleAlternatives.add(value)
                            }
                        }
                    }
                    CharResults("?", possibleAlternatives.toTypedArray())
                }
            }
        }
    }

    fun parseAccountNumberToString(input: String): String {
        return parseAccountNumber(input)
            .map { it.first }
            .reduce(String::plus)
    }

    fun parseAccountNumber(rawInput: String): List<CharResults> {
        val input = rawInput.filterNot { it == '\n' }
        val size = input.length / 9
        return input
            .chunked(3)
            .foldIndexed(MutableList(size) { "" }) { index, acc, chunk ->
                acc[index % acc.size] = "${acc[index % acc.size]}$chunk"
                acc
            }
            .map { parseCharResults(it) }
    }
}

object AccountNumberValidator {
    fun validate(accountNumber: String): Boolean {
        return !accountNumber.contains('?') && accountNumber
            .reversed()
            .foldRightIndexed(0)
            { index, char, acc ->
                acc + (index + 1) * char.digitToInt()
            }
            .let { it % 11 == 0 }
    }
}

object AccountNumberProcessor {
    fun process(accountNumber: String): String {
        val accountNumberObject = AccountNumber(accountNumber)
        val parsedAccount = accountNumberObject.accountNumber

        return if (!parsedAccount.contains("?") && AccountNumberValidator.validate(parsedAccount)) {
            parsedAccount
        } else {
            if(accountNumberObject.hasUnknownReplacements()) {
                "$parsedAccount ILL"
            } else {
                val validAlternatives = accountNumberObject.allAlternatives()
                    .filter(AccountNumberValidator::validate)

                if (validAlternatives.isEmpty()) {
                    "$parsedAccount ILL"
                } else if (validAlternatives.size == 1) {
                    validAlternatives.first()
                } else {
                    "$parsedAccount AMB [${validAlternatives.joinToString(", ") { "'$it'" }}]"
                }
            }
        }
    }

    fun convertNumbers(value: String) : Array<String> {
        return when(value) {
            "0" -> arrayOf("8")
            "1" -> arrayOf("7")
            "2" -> arrayOf()
            "3" -> arrayOf("9")
            "4" -> arrayOf()
            "5" -> arrayOf("6", "9")
            "6" -> arrayOf("5", "8")
            "7" -> arrayOf("1")
            "8" -> arrayOf("0", "6", "9")
            "9" -> arrayOf("3", "5", "8")
            else -> arrayOf()
        }
    }
}

class AccountNumber(rawAccountNumber: String) {
    private val parser = AccountNumberParser()
    private val processedDetails = parser.parseAccountNumber(rawAccountNumber)
    val accountNumber = processedDetails.map { it.first }.reduce(String::plus)

    fun hasUnknownReplacements(): Boolean {
        return processedDetails.any { it.first == "?" && it.second.isEmpty() }
    }

    fun allAlternatives(): List<String> {
       return  processedDetails.foldIndexed(mutableListOf()) { index, acc, charResults ->
            acc.addAll(
                charResults.second.map { alternative ->
                    accountNumber.substring(0, index) + alternative + accountNumber.substring(index + 1)
                }
            )

            acc
        }
    }
}
