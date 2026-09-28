package com.example

import android.app.Application
import com.example.data.local.AppDatabase
import com.example.data.repository.ServiceRepository

class AnupDigitalApp : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var repository: ServiceRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getDatabase(this)
        repository = ServiceRepository(
            favoriteDao = database.favoriteDao(),
            customServiceDao = database.customServiceDao()
        )
    }
}
