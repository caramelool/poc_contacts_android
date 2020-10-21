package com.caramelo.contacts.presenter

import com.caramelo.contacts.model.Contact
import com.caramelo.contacts.repository.ContactRepository
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    lateinit var presenter: MainPresenter

    @Mock
    lateinit var view: MainView

    @Mock
    lateinit var repository: ContactRepository

    @Before
    fun before() {
        presenter = MainPresenter(view, repository)
    }

    @Test
    fun `should show empty list without permission`() {
        runBlocking {
            whenever(view.requestContactPermission()).thenReturn(false)
            whenever(repository.getContacts(any())).thenReturn(emptyList())

            presenter.suspendRequest()

            inOrder(view, repository) {
                verify(view, times(1)).showLoading()
                verify(view, times(1)).requestContactPermission()
                verify(repository, times(1)).getContacts(false)
                verify(view, never()).showContacts(any())
                verify(view, times(1)).showEmpty()
                verify(view, never()).showError()
            }

        }
    }

    @Test
    fun `should show empty list without contacts`() {
        runBlocking {
            whenever(view.requestContactPermission()).thenReturn(true)
            whenever(repository.getContacts(any())).thenReturn(emptyList())

            presenter.suspendRequest()

            inOrder(view, repository) {
                verify(view, times(1)).showLoading()
                verify(view, times(1)).requestContactPermission()
                verify(repository, times(1)).getContacts(true)
                verify(view, never()).showContacts(any())
                verify(view, times(1)).showEmpty()
                verify(view, never()).showError()
            }

        }
    }

    @Test
    fun `should show error when throw illegal exception`() {
        runBlocking {
            whenever(view.requestContactPermission()).thenReturn(true)
            whenever(repository.getContacts(any())).thenThrow(IllegalArgumentException())

            presenter.suspendRequest()

            inOrder(view, repository) {
                verify(view, times(1)).showLoading()
                verify(view, times(1)).requestContactPermission()
                verify(repository, times(1)).getContacts(true)
                verify(view, never()).showContacts(any())
                verify(view, never()).showEmpty()
                verify(view, times(1)).showError()
            }

        }
    }

    @Test
    fun `should show contacts`() {
        runBlocking {
            val list = listOf<Contact>(mock())
            whenever(view.requestContactPermission()).thenReturn(true)
            whenever(repository.getContacts(any())).thenReturn(list)

            presenter.suspendRequest()

            inOrder(view, repository) {
                verify(view, times(1)).showLoading()
                verify(view, times(1)).requestContactPermission()
                verify(repository, times(1)).getContacts(true)
                verify(view, times(1)).showContacts(list)
                verify(view, never()).showEmpty()
                verify(view, never()).showError()
            }

        }
    }
}
