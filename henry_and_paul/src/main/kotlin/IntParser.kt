package org.yorkdevelopers

class IntParser {
    fun parseChar(input: String): Int {
        return when(input) {
            "   \n  |\n  |" -> 1
            else -> throw IllegalArgumentException("Unknown input")
        }
    }
}
