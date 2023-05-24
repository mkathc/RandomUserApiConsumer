package com.kath.randomapiconsumer.domain.repository

import com.kath.randomapiconsumer.domain.model.RandomUser

interface RandomUserRepository {
    suspend fun getRandomUsers(): Result<List<RandomUser>>
}