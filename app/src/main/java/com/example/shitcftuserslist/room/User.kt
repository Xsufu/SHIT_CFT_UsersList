package com.example.shitcftuserslist.room

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "street")
    val street: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "state")
    val state: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "latitude")
    val latitude: String,
    @ColumnInfo(name = "longitude")
    val longitude: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "dob")
    val dob: String,
    @ColumnInfo(name = "phone")
    val phone: String,
    @ColumnInfo(name = "cell")
    val cell: String,
    @ColumnInfo(name = "image")
    val image: String
)
