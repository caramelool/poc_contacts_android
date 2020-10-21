package com.caramelo.contacts.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey val uid: String,
    val name: String,
    val phone: String?,
    val email: String?,
    val isRegistered: Boolean
)
