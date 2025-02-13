package org.yorkdevelopers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UseCase4Test {

    @Test
    fun `invalid 111111111 replaced with 711111111`() {
        val result = AccountNumberProcessor.process(
            """                           
  |  |  |  |  |  |  |  |  |
  |  |  |  |  |  |  |  |  |"""
        )
        assertEquals("711111111", result)
    }

    @Test
    fun `invalid 777777777 replaced with 777777177`() {
        val result = AccountNumberProcessor.process(
            """ _  _  _  _  _  _  _  _  _ 
  |  |  |  |  |  |  |  |  |
  |  |  |  |  |  |  |  |  |"""
        )
        assertEquals("777777177", result)
    }

    @Test
    fun `invalid 200000000 replaced with 200800000`() {
        val result = AccountNumberProcessor.process(
            """ _  _  _  _  _  _  _  _  _ 
 _|| || || || || || || || |
|_ |_||_||_||_||_||_||_||_|"""
        )
        assertEquals("200800000", result)
    }

    // Changed the order of the results as the calculator came back with the same answers in a different order
    @Test
    fun `invalid 888888888 replaced with AMB ('888886888'', '888888988', '888888880')`() {
        val result = AccountNumberProcessor.process(
            """ _  _  _  _  _  _  _  _  _ 
|_||_||_||_||_||_||_||_||_|
|_||_||_||_||_||_||_||_||_|"""
        )
        assertEquals("888888888 AMB ['888886888', '888888988', '888888880']", result)
    }

    // Changed the order of the results as the calculator came back with the same answers in a different order
    @Test
    fun `invalid 555555555 replaced with AMB ('559555555', '555655555')`() {
        val result = AccountNumberProcessor.process(
            """ _  _  _  _  _  _  _  _  _ 
|_ |_ |_ |_ |_ |_ |_ |_ |_ 
 _| _| _| _| _| _| _| _| _|"""
        )
        assertEquals("555555555 AMB ['559555555', '555655555']", result)
    }

    // Changed the order of the results as the calculator came back with the same answers in a different order
    @Test
    fun `invalid 666666666 replaced with AMB ('686666666', '666566666')`() {
        val result = AccountNumberProcessor.process(
            """ _  _  _  _  _  _  _  _  _ 
|_ |_ |_ |_ |_ |_ |_ |_ |_ 
|_||_||_||_||_||_||_||_||_|"""
        )
        assertEquals("666666666 AMB ['686666666', '666566666']", result)
    }

    @Test
    fun `invalid 999999999 replaced with AMB ('899999999', '993999999', '999959999')`() {
        val result = AccountNumberProcessor.process(
            """ _  _  _  _  _  _  _  _  _ 
|_||_||_||_||_||_||_||_||_|
 _| _| _| _| _| _| _| _| _|"""
        )
        assertEquals("999999999 AMB ['899999999', '993999999', '999959999']", result)
    }

    // Changed the order of the results as the calculator came back with the same answers in a different order
    @Test
    fun `invalid 490067715 replaced with AMB ('490867715', '490067115', '490067719')`() {
        val result = AccountNumberProcessor.process(
            """    _  _  _  _  _  _     _ 
|_||_|| || ||_   |  |  ||_ 
  | _||_||_||_|  |  |  | _|"""
        )
        assertEquals("490067715 AMB ['490867715', '490067115', '490067719']", result)
    }

    @Test
    fun `invalid ?23456789 replaced with 123456789`() {
        val result = AccountNumberProcessor.process(
            """    _  _     _  _  _  _  _ 
 _| _| _||_||_ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _|"""
        )
        assertEquals("123456789", result)
    }

    @Test
    fun `invalid 0?000005 replaced with 000000051`() {
        val result = AccountNumberProcessor.process(
            """ _     _  _  _  _  _  _    
| || || || || || || ||_   |
|_||_||_||_||_||_||_| _|  |"""
        )
        assertEquals("000000051", result)
    }

    @Test
    fun `invalid 490867716 replaced with 490867715`() {
        val result = AccountNumberProcessor.process(
            """    _  _  _  _  _  _     _ 
|_||_|| ||_||_   |  |  | _ 
  | _||_||_||_|  |  |  | _|"""
        )
        assertEquals("490867715", result)
    }
}
