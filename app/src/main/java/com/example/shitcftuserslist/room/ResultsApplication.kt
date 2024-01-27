package com.example.shitcftuserslist.room

import android.app.Application

class ResultsApplication: Application() {
    val database: UserRoomDatabase by lazy { UserRoomDatabase.getDatabase(this) }
}