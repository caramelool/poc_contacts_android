package com.caramelo.contacts.di.core

import android.content.Context
import com.caramelo.contacts.database.local.ContactLocal
import com.caramelo.contacts.repository.ContactRepository
import kotlin.reflect.KProperty

interface RepositoryDependency {
    val contactRepository: ContactRepository
}

class RepositoryInject(context: Context) : RepositoryDependency,
    DatabaseDependency by DatabaseInject(context),
    ServiceDependency by ServiceInject() {

    operator fun getValue(ref: Any, property: KProperty<*>): RepositoryDependency {
        return this
    }

    override val contactRepository: ContactRepository
        get() = ContactRepository(contactService, contactDao, contactLocal)

    private val contactLocal = ContactLocal(context.contentResolver)
}
