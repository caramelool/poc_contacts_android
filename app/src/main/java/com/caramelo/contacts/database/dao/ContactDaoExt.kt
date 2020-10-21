package com.caramelo.contacts.database.dao

import com.caramelo.contacts.database.entity.ContactEntity
import com.caramelo.contacts.service.response.ContactResponse

suspend fun ContactDao.insert(vararg contract: ContactResponse) {
    contract.map {
        ContactEntity(
            it.id,
            it.name,
            it.phoneNumber,
            it.emailAddress,
            it.isRegistered
        )
    }.also { insert(*it.toTypedArray()) }
}
