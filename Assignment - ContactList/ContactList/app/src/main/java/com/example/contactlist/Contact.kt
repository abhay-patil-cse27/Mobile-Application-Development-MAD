package com.example.contactlist

import java.io.Serializable

data class Contact(
    val imageResId: Int,
    val name: String,
    val phoneNumber: String
) : Serializable
