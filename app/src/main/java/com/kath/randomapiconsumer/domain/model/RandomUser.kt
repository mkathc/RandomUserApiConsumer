package com.kath.randomapiconsumer.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RandomUser(
    val id: String,
    val name: String,
    val userName: String,
    val pictureMedium: String,
    val pictureLarge: String,
    val genre: String,
    val email: String,
    val phone: String,
    val direction: String,
    val lastName: String,
    val age: Int
) : Parcelable
