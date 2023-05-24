package com.kath.randomapiconsumer

import android.app.Application
import com.kath.randomapiconsumer.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RandomApiConsumerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@RandomApiConsumerApp)
            modules(appModule)
        }
    }
}