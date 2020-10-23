package com.caramelo.contacts.di.core

import androidx.room.Room
import com.caramelo.contacts.database.AppDatabase
import com.caramelo.contacts.database.dao.ContactDao
import com.caramelo.contacts.database.local.ContactLocal

interface DatabaseDependency {
    val contactDao: ContactDao
    val contactLocal: ContactLocal
}

class DatabaseInject : DatabaseDependency,
    ApplicationDependency by ApplicationInject {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "contacts-app-database"
        ).build()
    }

    override val contactDao by lazy { db.contactDao() }

    override val contactLocal by lazy { ContactLocal(applicationContext.contentResolver) }
}
