package com.example.shitcftuserslist.network

import com.example.shitcftuserslist.data.UserResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MainAPI {
    @GET("api")
    suspend fun getUsersData(
        //@Query("results") results: Int
    ): UserResult
}