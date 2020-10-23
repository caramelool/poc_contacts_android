package com.caramelo.contacts.di

import com.caramelo.contacts.di.core.RepositoryDependency
import com.caramelo.contacts.di.core.RepositoryInject
import com.caramelo.contacts.presenter.MainPresenter
import com.caramelo.contacts.presenter.MainView

import kotlin.reflect.KProperty

class MainPresenterInject : RepositoryDependency by RepositoryInject() {
    operator fun getValue(ref: MainView, property: KProperty<*>): MainPresenter {
        return MainPresenter(ref, contactRepository)
    }
}
