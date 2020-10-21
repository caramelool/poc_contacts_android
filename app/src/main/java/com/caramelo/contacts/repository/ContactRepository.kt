package com.caramelo.contacts.repository

import com.caramelo.contacts.database.dao.ContactDao
import com.caramelo.contacts.database.dao.insert
import com.caramelo.contacts.database.local.ContactLocal
import com.caramelo.contacts.model.Contact
import com.caramelo.contacts.service.ContactService
import timber.log.Timber

class ContactRepository(
    private val contactService: ContactService,
    private val contactDao: ContactDao,
    private val contactLocal: ContactLocal
) {
    suspend fun getContacts(hasPermission: Boolean): List<Contact> {
        try {
            val listLocal = contactLocal.getAll(hasPermission)
            val listService = contactService.checkList(listLocal)

            if (listService.isNotEmpty()) {
                contactDao.insert(*listService.toTypedArray())
            }

            return contactDao.getAll().map {
                Contact(
                    it.name,
                    it.phone ?: "(nothing)",
                    it.email ?: "(nothing)"
                )
            }
        } catch (e: IllegalArgumentException) {
            Timber.e(e)
            throw e
        } catch (e: Throwable) {
            Timber.e(e)
            throw IllegalArgumentException(e)
        }
    }
}