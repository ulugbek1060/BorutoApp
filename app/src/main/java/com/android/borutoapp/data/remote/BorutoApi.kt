package com.android.borutoapp.data.remote

import com.android.borutoapp.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BorutoApi {

    @GET("/boruto/heroes")
    suspend fun getAllHeroes(
        @Query("page") pageIndex: Int
    ): ApiResponse

    @GET("/boruto/heroes/search")
    suspend fun searchHeroes(
        @Query("name") query: String
    ): ApiResponse
}