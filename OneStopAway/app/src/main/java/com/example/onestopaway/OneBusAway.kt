package com.example.onestopaway

import android.app.Application
import android.provider.ContactsContract

class OneBusAway: Application() {
    val database: DatabaseManager by lazy { DatabaseManager.getDatabase(this) }
    val repository: DataRepository by lazy { DataRepository(database)}


}