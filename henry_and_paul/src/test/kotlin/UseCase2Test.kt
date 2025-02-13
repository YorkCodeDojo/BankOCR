package org.yorkdevelopers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UseCase2Test {

    @ParameterizedTest(name = "Ensure that {0} is valid")
    @ValueSource(strings = ["000000000", "100000010", "200000012"])
    fun `Ensure that is valid`(input: String) {
        val valid = AccountNumberValidator.validate(input)
            assertEquals(true, valid)
    }

    @ParameterizedTest(name = "Ensure that {0} is valid")
    @ValueSource(strings = ["000000001", "000000002", "000000003", "000000004", "000000005", "000000006", "000000007", "000000008", "000000009"])
    fun `Ensure that is invalid`(input:String) {
        val valid = AccountNumberValidator.validate(input)
        assertEquals(false, valid)
    }


}


