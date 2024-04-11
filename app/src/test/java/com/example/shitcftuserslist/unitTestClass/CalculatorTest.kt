package com.example.shitcftuserslist.unitTestClass

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class CalculatorTest {
    private val calculator = Calculator()

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
}