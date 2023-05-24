package com.kath.randomapiconsumer.data.api

import com.kath.randomapiconsumer.data.model.RandomUserResult
import retrofit2.Response
import retrofit2.http.GET

interface RandomUserApi {
    @GET("?results=20")
    suspend fun getRandomUsers(): Response<RandomUserResult>
}