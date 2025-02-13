package org.yorkdevelopers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IntParserTest {
    @Test
    fun `Load one from the weird string`() {
        val intParser = IntParser()
        val result = intParser.parseChar(
"""   
  |
  |""")
        assertEquals(1, result)
    }
}




