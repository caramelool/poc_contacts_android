package com.caramelo.contacts.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.caramelo.contacts.database.entity.ContactEntity

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    suspend fun getAll(): Array<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg contract: ContactEntity)
}
