package org.yorkdevelopers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AccountNumberParserTest {
    @Test
    fun `Load one from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseChar(
"""   
  |
  |""")
        assertEquals("1", result)
    }

    @Test
    fun `Load two from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseChar(
""" _ 
 _|
|_ """)
        assertEquals("2", result)
    }

    @Test
    fun `Load three from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseChar(
""" _ 
 _|
 _|""")
        assertEquals("3", result)
    }

    @Test
    fun `Load four from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseChar(
"""   
|_|
  |""")
        assertEquals("4", result)
    }

    @Test
    fun `Load five from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseChar(
            """ _ 
|_ 
 _|""")
        assertEquals("5", result)
    }

    @Test
    fun `Load six from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseChar(
            """ _ 
|_ 
|_|""")
        assertEquals("6", result)
    }

    @Test
    fun `Load seven from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseChar(
            """ _ 
  |
  |""")
        assertEquals("7", result)
    }

    @Test
    fun `Load eight from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseChar(
            """ _ 
|_|
|_|""")
        assertEquals("8", result)
    }

    @Test
    fun `Load nine from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseChar(
            """ _ 
|_|
 _|""")
        assertEquals("9", result)
    }

    @Test
    fun `Load zero from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseChar(
            """ _ 
| |
|_|""")
        assertEquals("0", result)
    }

    @Test
    fun `Load single zero account number from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseAccountNumber(
            """ _ 
| |
|_|""")
        assertEquals("0", result)
    }

    @Test
    fun `Load account number from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseAccountNumber(
            """ _  _  _  _  _  _  _  _  _ 
| || || || || || || || || |
|_||_||_||_||_||_||_||_||_|""")
        assertEquals("000000000", result)
    }

}


