package com.caramelo.contacts.presenter

import com.caramelo.contacts.model.Contact
import com.caramelo.contacts.repository.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainPresenter(
    private val view: MainView,
    private val repository: ContactRepository
) : CoroutineScope by MainScope() {

    fun request() {
        launch {
            suspendRequest()
        }
    }

    internal suspend fun suspendRequest() {
        try {
            view.showLoading()
            val hasPermission = view.requestContactPermission()
            val contacts = repository.getContacts(hasPermission)
            if (contacts.isNotEmpty()) {
                view.showContacts(contacts)
            } else {
                view.showEmpty()
            }
        } catch (e: IllegalArgumentException) {
            view.showError()
        }
    }
}

interface MainView {
    suspend fun requestContactPermission(): Boolean
    fun showLoading()
    fun showContacts(contacts: List<Contact>)
    fun showEmpty()
    fun showError()
}
