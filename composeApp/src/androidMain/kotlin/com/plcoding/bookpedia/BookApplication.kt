package com.plcoding.bookpedia

import android.app.Application
import com.plcoding.bookpedia.dInjection.initCoin
import org.koin.android.ext.koin.androidContext

class BookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //we can add dependencies that will only be used on android here
        initCoin {
            androidContext(this@BookApplication)
        }
    }
}