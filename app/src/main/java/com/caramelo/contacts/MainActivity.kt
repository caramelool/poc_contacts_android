package com.caramelo.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.caramelo.contacts.adapter.ContactAdapter
import com.caramelo.contacts.di.MainActivityInject
import com.caramelo.contacts.model.Contact
import com.caramelo.contacts.presenter.MainView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity(), MainView {

    companion object {
        private const val REQUEST_PERMISSION_CONTACT = 10
    }

    private var permissionContinuation: Continuation<Boolean>? = null

    private val presenter by MainActivityInject()
    private val adapter = ContactAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        presenter.request()
    }

    private fun setupView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            presenter.request()
        }
    }

    override fun showLoading() {
        adapter.showLoading()
    }

    override fun showContacts(contacts: List<Contact>) {
        adapter.set(contacts)
    }

    override fun showEmpty() {
        adapter.showEmpty()
    }

    override fun showError() {
        adapter.showError()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CONTACT) {
            if (checkContactPermission()) {
                permissionContinuation?.resume(true)
            } else {
                permissionContinuation?.resume(false)
            }
        }
    }

    override suspend fun requestContactPermission(): Boolean {
        return suspendCoroutine {
            permissionContinuation = it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_PERMISSION_CONTACT
            )
        }
    }

    private fun checkContactPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }
}