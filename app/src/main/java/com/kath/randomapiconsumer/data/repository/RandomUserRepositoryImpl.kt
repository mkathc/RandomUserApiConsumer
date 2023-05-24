package com.kath.randomapiconsumer.data.repository

import com.kath.randomapiconsumer.data.api.RandomUserApi
import com.kath.randomapiconsumer.data.model.RandomUserResult
import com.kath.randomapiconsumer.domain.model.RandomUser
import com.kath.randomapiconsumer.domain.repository.RandomUserRepository

internal class RandomUserRepositoryImpl(
    private val randomUserApi: RandomUserApi,
) : RandomUserRepository {


    override suspend fun getRandomUsers(): Result<List<RandomUser>> {
        val randomList = randomUserApi.getRandomUsers()

        return if (randomList.isSuccessful) {
            val distinct: List<RandomUser> = LinkedHashSet(randomList.body()!!.parseSuccess()).toList()
            Result.success(distinct)
        } else {
            Result.failure(Throwable())
        }
    }

    private fun RandomUserResult.parseSuccess(): List<RandomUser> {
        val randomUserList = mutableListOf<RandomUser>()
        this.results.forEach {
            randomUserList.add(
                RandomUser(
                    id = it.login.uuid,
                    name = "${it.name.title} ${it.name.first} ${it.name.last}",
                    lastName = it.name.last,
                    age = it.dob.age,
                    genre = it.gender,
                    pictureLarge = it.picture.large,
                    pictureMedium = it.picture.medium,
                    userName = it.login.username,
                    email = it.email,
                    phone = it.phone,
                    direction = "${it.location.street.number} ${it.location.street.name} ${it.location.city} ${it.location.state} ${it.location.postcode}",
                )
            )
        }
        return randomUserList
    }
}