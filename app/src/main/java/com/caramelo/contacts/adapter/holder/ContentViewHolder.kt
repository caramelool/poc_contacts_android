package com.caramelo.contacts.adapter.holder

import android.view.ViewGroup
import com.caramelo.contacts.R
import com.caramelo.contacts.model.Contact
import kotlinx.android.synthetic.main.view_contact_content.view.*

class ContentViewHolder(
    parent: ViewGroup
) : AbstractViewHolder(parent, R.layout.view_contact_content) {

    fun bind(contact: Contact) {
        with(itemView) {
            name.text = contact.name
            email.text = contact.email
            phoneNumber.text = contact.phone
        }
    }
}