package com.caramelo.contacts.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caramelo.contacts.adapter.holder.ContentViewHolder
import com.caramelo.contacts.adapter.holder.EmptyViewHolder
import com.caramelo.contacts.adapter.holder.ErrorViewHolder
import com.caramelo.contacts.adapter.holder.LoadingViewHolder
import com.caramelo.contacts.model.Contact

class ContactAdapter(
    private var data: List<Contact> = emptyList()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class State(val viewType: Int) {
        Content(0),
        Loading(1),
        Empty(2),
        Error(3)
    }

    private var currentState = State.Content

    override fun getItemViewType(position: Int): Int {
        return currentState.viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            State.Content.viewType -> ContentViewHolder(parent)
            State.Loading.viewType -> LoadingViewHolder(parent)
            State.Empty.viewType -> EmptyViewHolder(parent)
            State.Error.viewType -> ErrorViewHolder(parent)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContentViewHolder -> holder.bind(data[position])
        }
    }

    override fun getItemCount() = if (currentState == State.Content) data.size else 1

    fun set(data: List<Contact>) {
        currentState = State.Content
        this.data = data
        notifyDataSetChanged()
    }

    fun showLoading() {
        currentState = State.Loading
        notifyDataSetChanged()
    }

    fun showEmpty() {
        currentState = State.Empty
        notifyDataSetChanged()
    }

    fun showError() {
        currentState = State.Error
        notifyDataSetChanged()
    }

}