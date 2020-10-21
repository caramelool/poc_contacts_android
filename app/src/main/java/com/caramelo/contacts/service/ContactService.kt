package com.caramelo.contacts.service

import com.caramelo.contacts.database.entity.ContactEntity
import com.caramelo.contacts.service.response.ContactResponse
import kotlinx.coroutines.delay
import java.lang.IllegalArgumentException

interface ContactService {
    suspend fun checkList(list: List<ContactEntity>): List<ContactResponse>
}

class ContactServiceImpl : ContactService {
    override suspend fun checkList(list: List<ContactEntity>): List<ContactResponse> {
        delay(2000)
        list.find { it.name == "error" }?.let { throw IllegalArgumentException("Invalid contact $it") }
        return list.map { ContactResponse(it.uid, it.name, it.phone, it.email, true) }
    }
}