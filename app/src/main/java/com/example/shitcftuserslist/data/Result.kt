package com.example.shitcftuserslist.data

data class Result(
    val cell: String,
    val dob: Dob,
    val email: String,
    val gender: String,
    val location: Location,
    val name: Name,
    val phone: String,
    val picture: Picture,
    val registered: Registered
)