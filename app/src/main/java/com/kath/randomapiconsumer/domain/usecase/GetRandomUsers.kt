package com.kath.randomapiconsumer.domain.usecase

import com.kath.randomapiconsumer.domain.model.RandomUser
import com.kath.randomapiconsumer.domain.repository.RandomUserRepository


interface GetRandomUsersUseCase {
    suspend operator fun invoke(): List<RandomUser>
}

internal class GetRandomUsers constructor(
    private val randomUserRepository: RandomUserRepository,
) : GetRandomUsersUseCase {
    override suspend operator fun invoke(): List<RandomUser> {
        randomUserRepository.getRandomUsers().fold(
            onSuccess = {
                return it
            },
            onFailure = {
                return mutableListOf()
            }
        )
    }
}