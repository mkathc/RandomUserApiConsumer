package com.kath.randomapiconsumer.data.model

class RandomUserResult(
    val results: List<RandomUserResponse>,
    val info: RandomInfo
)

class RandomInfo(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)

class RandomUserResponse(
    val name: RandomUserName,
    val picture: RandomUserPicture,
    val location: RandomUserLocation,
    val email: String,
    val phone: String,
    val login: RandomUserLogin,
    val gender: String,
    val dob: RandomUserAge
)

class RandomUserName(
    val title: String,
    val first: String,
    val last: String
)

data class RandomUserPicture(
    val large: String,
    val medium: String,
    val thumbnailast: String
)

data class RandomUserLocation(
    val street: RandomUserStreet,
    val city: String,
    val state: String,
    val postcode:String
)

data class RandomUserStreet(
    val number: Int,
    val name: String
)

data class RandomUserLogin(
    val uuid: String,
    val username: String
)

data class RandomUserAge(
    val date: String,
    val age: Int
)