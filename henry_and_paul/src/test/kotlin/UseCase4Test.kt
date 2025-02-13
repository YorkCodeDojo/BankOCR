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

    @Test
    fun `invalid 888888888 replaced with AMB ('888886888', '888888880', '888888988')`() {
        val result = AccountNumberProcessor.process(
            """ _  _  _  _  _  _  _  _  _ 
|_||_||_||_||_||_||_||_||_|
|_||_||_||_||_||_||_||_||_|"""
        )
        assertEquals("888888888 AMB ['888886888', '888888880', '888888988']", result)
    }

    @Test
    fun `invalid 555555555 replaced with AMB ('555655555', '559555555')`() {
        val result = AccountNumberProcessor.process(
            """ _  _  _  _  _  _  _  _  _ 
|_ |_ |_ |_ |_ |_ |_ |_ |_ 
 _| _| _| _| _| _| _| _| _|"""
        )
        assertEquals("555555555 AMB ['555655555', '559555555']", result)
    }

    @Test
    fun `invalid 666666666 replaced with AMB ('666566666', '686666666')`() {
        val result = AccountNumberProcessor.process(
            """ _  _  _  _  _  _  _  _  _ 
|_ |_ |_ |_ |_ |_ |_ |_ |_ 
|_||_||_||_||_||_||_||_||_|"""
        )
        assertEquals("666666666 AMB ['666566666', '686666666']", result)
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

    @Test
    fun `invalid 490067715 replaced with AMB ('490067115', '490067719', '490867715')`() {
        val result = AccountNumberProcessor.process(
            """    _  _  _  _  _  _     _ 
|_||_|| || ||_   |  |  ||_ 
  | _||_||_||_|  |  |  | _|"""
        )
        assertEquals("490067715 AMB ['490067115', '490067719', '490867715']", result)
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
