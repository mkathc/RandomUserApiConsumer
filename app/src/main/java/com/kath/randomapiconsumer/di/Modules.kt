package com.kath.randomapiconsumer.di

import com.kath.randomapiconsumer.data.api.RandomUserApi
import com.kath.randomapiconsumer.data.repository.RandomUserRepositoryImpl
import com.kath.randomapiconsumer.domain.repository.RandomUserRepository
import com.kath.randomapiconsumer.domain.usecase.GetRandomUsers
import com.kath.randomapiconsumer.domain.usecase.GetRandomUsersUseCase
import com.kath.randomapiconsumer.ui.list.ListViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApiService(get(), RandomUserApi::class.java) }
    single<RandomUserRepository> { RandomUserRepositoryImpl(get()) }
    single<GetRandomUsersUseCase> { GetRandomUsers(get()) }
    viewModelOf(::ListViewModel)
}


fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().baseUrl("https://api.randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient).build()

fun provideApiService(retrofit: Retrofit, apiService: Class<RandomUserApi>) =
    createService(retrofit, apiService)

fun <T> createService(retrofit: Retrofit, serviceClass: Class<T>): T = retrofit.create(serviceClass)
