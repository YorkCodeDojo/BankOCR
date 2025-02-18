package org.yorkdevelopers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UseCase3Test {

    @Test
    fun `test account number with no issue 000000051 from the weird string`() {
        val result = AccountNumberProcessor.process(
            """ _  _  _  _  _  _  _  _    
| || || || || || || ||_   |
|_||_||_||_||_||_||_| _|  |"""
        )
        assertEquals("000000051", result)
    }


    @Test
    fun `Load corrupt account number one from the weird string`() {
        val result = AccountNumberProcessor.process(
            """    _  _  _  _  _  _     _ 
|_||_|| || ||_   |  |  | _ 
  | _||_||_||_|  |  |  | _|"""
        )
        assertEquals("49006771? ILL", result)
    }

    @Test
    fun `Load corrupt account number two from the weird string`() {
        val result = AccountNumberProcessor.process(
            """    _  _     _  _  _  _  _ 
  | _| _||_| _ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _ """
        )
        assertEquals("1234?678? ILL", result)
    }


}
