package com.example.shitcftuserslist.unitTestClass

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import java.util.stream.Stream

class CalculatorTest {
    private val calculator = Calculator()

    private companion object {

        @JvmStatic
        fun data(): Stream<Arguments> = Stream.of(
            arguments(3.0, 2.0, 9.0),
            arguments(5.0, 0.0, 1.0),
            arguments(4.0, -2.0, 0.0625)
        )
    }

    @Test
    fun `WHEN 2 is raised to power 5 EXPECT 32`() {
        val expected: Double = 32.0

        val actual = calculator.exponentiation(number = 2.0, power = 5.0)

        assertEquals(expected, actual)
    }

    @Test
    fun `WHEN 4 is adds up to 7 EXPECT 11`() {
        val expected: Int = 11

        val actual = calculator.addition(firstSummand = 4, sndSummand = 7)

        assertEquals(expected, actual)
    }

    @Test
    fun `WHEN 17 is subtracted from 32 EXPEXT 15`() {
        val expected: Int = 15

        val actual = calculator.difference(minuend = 32, subtrahend = 17)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("data")
    fun `WHEN raise to power EXPECT correct result`(number: Double, power: Double, expected: Double) {
        val actual = calculator.exponentiation(number, power)

        assertEquals(expected, actual)
    }

    @Test
    fun `WHEN 0 is power of 0 EXPECT illegal argument exception`() {
        assertThrows<IllegalArgumentException> {
            calculator.exponentiation(0.0, 0.0)
        }
    }

    private val secretCode: SecretCode = mock()
    private val publicCodeCalculator = PublicCodeCalculator(secretCode)

    @Test
    fun `WHEN get publicCode with key 8 EXPECT 2049`() {
        whenever(secretCode.hardCalculations()) doReturn (2041.0)

        val key = 8.0

        val expected = 2049.0

        val actual = publicCodeCalculator.getPublicKey(key)

        assertEquals(expected, actual)
    }
}