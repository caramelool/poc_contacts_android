package com.caramelo.contacts.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.caramelo.contacts.database.dao.ContactDao
import com.caramelo.contacts.database.entity.ContactEntity

@Database(entities = [ContactEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}