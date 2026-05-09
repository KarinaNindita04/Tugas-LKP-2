package com.example.myapplication.network

import com.example.myapplication.Model.Food
import retrofit2.http.GET

interface ApiService {

    @GET("foods.json")
    suspend fun getFoods(): List<Food>
}