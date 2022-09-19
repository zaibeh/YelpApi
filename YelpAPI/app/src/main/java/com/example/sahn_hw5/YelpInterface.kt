package com.example.sahn_hw5

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpInterface {
    @GET("businesses/search")
    fun getRestaurantInfo(
                        @Header("Authorization") author: String,
                        @Query("term") name: String,
                        @Query("location") location: String) : Call<YelpData>



}