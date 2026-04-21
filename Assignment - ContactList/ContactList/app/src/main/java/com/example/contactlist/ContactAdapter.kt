package com.example.contactlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    private val contacts: List<Contact>,
    private val onItemClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivContactImage: ImageView = view.findViewById(R.id.ivContactImage)
        val tvContactName: TextView = view.findViewById(R.id.tvContactName)
        val tvContactPhone: TextView = view.findViewById(R.id.tvContactPhone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.tvContactName.text = contact.name
        holder.tvContactPhone.text = contact.phoneNumber
        holder.ivContactImage.setImageResource(contact.imageResId)
        
        holder.itemView.setOnClickListener {
            onItemClick(contact)
        }
    }

    override fun getItemCount() = contacts.size
}
