package com.caramelo.contacts.database.local

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.caramelo.contacts.database.entity.ContactEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactLocal(
    private val contentResolver: ContentResolver
) {
    suspend fun getAll(hasPermission: Boolean): List<ContactEntity> = withContext(Dispatchers.IO) {
        if (hasPermission.not()) return@withContext emptyList<ContactEntity>()
        contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        ).use { cursor ->
            if (cursor == null) return@withContext emptyList<ContactEntity>()
            val list = mutableListOf<ContactEntity>()
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id: String = cursor.id()
                    val name: String = cursor.displayName()
                    val phoneNumber: String? = getPhoneNumber(id)
                    val email: String? = getEmail(id)
                    list += ContactEntity(id, name, phoneNumber, email, false)
                }
            }
            return@withContext list
        }
    }

    private fun getPhoneNumber(id: String): String? {
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(id),
            null
        ).use { cursor ->
            return cursor?.let {
                if (it.count > 0) {
                    it.moveToFirst()
                    it.phoneNumber()
                } else null
            }
        }
    }

    private fun getEmail(id: String): String? {
        contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
            arrayOf(id),
            null
        ).use { cursor ->
            return cursor?.let {
                if (it.count > 0) {
                    it.moveToFirst()
                    it.email()
                } else null
            }
        }
    }

    private fun Cursor.id(): String {
        val const = ContactsContract.Contacts._ID
        return string(const)
    }

    private fun Cursor.displayName(): String {
        val const = ContactsContract.Contacts.DISPLAY_NAME
        return string(const)
    }

    private fun Cursor.hasPhoneNumber(): Boolean {
        val const = ContactsContract.Contacts.HAS_PHONE_NUMBER
        return integer(const) > 0
    }

    private fun Cursor.phoneNumber(): String {
        val const = ContactsContract.CommonDataKinds.Phone.NUMBER
        return string(const)
    }

    private fun Cursor.email(): String {
        val const = ContactsContract.CommonDataKinds.Email.ADDRESS
        return string(const)
    }

    private fun Cursor.string(const: String, default: String = ""): String {
        val columnIndex = getColumnIndex(const)
        return getStringOrNull(columnIndex) ?: default
    }

    private fun Cursor.integer(const: String, default: Int = 0): Int {
        val columnIndex = getColumnIndex(const)
        return getIntOrNull(columnIndex) ?: default
    }

}