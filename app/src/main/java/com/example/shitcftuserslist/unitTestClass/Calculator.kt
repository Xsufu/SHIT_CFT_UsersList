package com.example.shitcftuserslist.unitTestClass

import kotlin.math.pow

class Calculator {
    fun exponentiation(number: Double, power: Double): Double =
        if (number != 0.0 && power != 0.0) {
            number.pow(power)
        } else {
            throw IllegalArgumentException("0 in power of 0 is undefined")
        }


    fun addition(firstSummand: Int, sndSummand: Int): Int = firstSummand + sndSummand

    fun difference (minuend: Int, subtrahend: Int): Int = minuend - subtrahend
}