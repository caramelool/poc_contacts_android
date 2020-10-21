package com.caramelo.contacts.service.response

data class ContactResponse(
    val id: String,
    val name: String,
    val phoneNumber: String?,
    val emailAddress: String?,
    val isRegistered: Boolean
)
