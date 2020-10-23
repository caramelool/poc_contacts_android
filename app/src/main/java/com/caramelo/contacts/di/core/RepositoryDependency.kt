package com.caramelo.contacts.di.core

import com.caramelo.contacts.repository.ContactRepository

interface RepositoryDependency {
    val contactRepository: ContactRepository
}

class RepositoryInject : RepositoryDependency,
    DatabaseDependency by DatabaseInject(),
    ServiceDependency by ServiceInject() {

    override val contactRepository: ContactRepository
        get() = ContactRepository(contactService, contactDao, contactLocal)
}
