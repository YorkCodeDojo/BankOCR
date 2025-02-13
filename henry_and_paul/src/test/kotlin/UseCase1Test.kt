package org.yorkdevelopers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UseCase1Test {

    @Test
    fun `Load account number 000000000 from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseAccountNumber(
            """ _  _  _  _  _  _  _  _  _ 
| || || || || || || || || |
|_||_||_||_||_||_||_||_||_|""")
        assertEquals("000000000", result)
    }

    @Test
    fun `Load account number 111111111 from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseAccountNumber(
            """                           
  |  |  |  |  |  |  |  |  |
  |  |  |  |  |  |  |  |  |""")
        assertEquals("111111111", result)
    }

    @Test
    fun `Load account number 222222222 from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseAccountNumber(
            """ _  _  _  _  _  _  _  _  _ 
 _| _| _| _| _| _| _| _| _|
|_ |_ |_ |_ |_ |_ |_ |_ |_ """)
        assertEquals("222222222", result)
    }

    @Test
    fun `Load account number 33333333 from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseAccountNumber(
            """ _  _  _  _  _  _  _  _  _ 
 _| _| _| _| _| _| _| _| _|
 _| _| _| _| _| _| _| _| _|""")
        assertEquals("333333333", result)
    }

    @Test
    fun `Load account number 444444444 from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseAccountNumber(
            """                           
|_||_||_||_||_||_||_||_||_|
  |  |  |  |  |  |  |  |  |""")
        assertEquals("444444444", result)
    }

    @Test
    fun `Load account number 555555555 from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseAccountNumber(
            """ _  _  _  _  _  _  _  _  _ 
|_ |_ |_ |_ |_ |_ |_ |_ |_ 
 _| _| _| _| _| _| _| _| _|""")
        assertEquals("555555555", result)
    }

    @Test
    fun `Load account number 666666666 from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseAccountNumber(
            """ _  _  _  _  _  _  _  _  _ 
|_ |_ |_ |_ |_ |_ |_ |_ |_ 
|_||_||_||_||_||_||_||_||_|""")
        assertEquals("666666666", result)
    }

    @Test
    fun `Load account number 777777777 from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseAccountNumber(
            """ _  _  _  _  _  _  _  _  _ 
  |  |  |  |  |  |  |  |  |
  |  |  |  |  |  |  |  |  |""")
        assertEquals("777777777", result)
    }

    @Test
    fun `Load account number 888888888 from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseAccountNumber(
            """ _  _  _  _  _  _  _  _  _ 
|_||_||_||_||_||_||_||_||_|
|_||_||_||_||_||_||_||_||_|""")
        assertEquals("888888888", result)
    }

    @Test
    fun `Load account number 999999999 from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseAccountNumber(
            """ _  _  _  _  _  _  _  _  _ 
|_||_||_||_||_||_||_||_||_|
 _| _| _| _| _| _| _| _| _|""")
        assertEquals("999999999", result)
    }

    @Test
    fun `Load account number 123456789 from the weird string`() {
        val accountNumberParser = AccountNumberParser()
        val result = accountNumberParser.parseAccountNumber(
            """    _  _     _  _  _  _  _ 
  | _| _||_||_ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _|""")
        assertEquals("123456789", result)
    }

}


