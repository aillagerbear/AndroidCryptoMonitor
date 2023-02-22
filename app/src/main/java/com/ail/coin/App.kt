package com.ail.coin

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import timber.log.Timber

class App : Application() {

    init {
        instance = this
    }
    companion object {
        private var instance: App? = null
        fun context(): Context {
            return instance!!.applicationContext
        }

    }
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}