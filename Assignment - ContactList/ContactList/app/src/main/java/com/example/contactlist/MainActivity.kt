package com.example.contactlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val contactList = arrayListOf(
            Contact(R.mipmap.ic_launcher, "Abhay Patil", "123-456-7890"),
            Contact(R.mipmap.ic_launcher, "Shrirang Kulkarni", "987-654-3210"),
            Contact(R.mipmap.ic_launcher, "Harsh Sathe", "555-123-4567"),
            Contact(R.mipmap.ic_launcher, "Niranjan Pawar", "444-555-6666"),
            Contact(R.mipmap.ic_launcher, "Shrirang Kulkarni", "111-222-3333")
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ContactAdapter(contactList) { contact ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("contact", contact)
            startActivity(intent)
        }
    }
}
