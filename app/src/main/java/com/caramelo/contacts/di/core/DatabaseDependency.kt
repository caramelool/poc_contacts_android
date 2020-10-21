package com.caramelo.contacts.di.core

import android.content.Context
import androidx.room.Room
import com.caramelo.contacts.database.AppDatabase
import com.caramelo.contacts.database.dao.ContactDao

interface DatabaseDependency {
    val contactDao: ContactDao
}

class DatabaseInject(context: Context) :
    DatabaseDependency {
    private val db by lazy {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "contacts-app-database"
        ).build()
    }

    override val contactDao = db.contactDao()
}
