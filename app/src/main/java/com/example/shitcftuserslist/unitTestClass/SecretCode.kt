package com.example.shitcftuserslist.unitTestClass

import kotlin.math.pow

interface SecretCode {
    fun hardCalculations(): Double =
        4.0.pow(5) * 2.0 - (4.0 + 3.0)
}