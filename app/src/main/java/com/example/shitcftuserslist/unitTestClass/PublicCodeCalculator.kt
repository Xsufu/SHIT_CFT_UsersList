package com.example.shitcftuserslist.unitTestClass

class PublicCodeCalculator(private val secretCode: SecretCode) {

    fun getPublicKey(argument: Double) =
        argument + secretCode.hardCalculations()
}