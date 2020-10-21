package com.caramelo.contacts.di.core

import com.caramelo.contacts.service.ContactService
import com.caramelo.contacts.service.ContactServiceImpl

interface ServiceDependency {
    val contactService: ContactService
}

class ServiceInject : ServiceDependency {
    override val contactService: ContactService
        get() = ContactServiceImpl()
}
