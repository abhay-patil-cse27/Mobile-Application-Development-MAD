package com.example.contactlist

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val contact = intent.getSerializableExtra("contact") as? Contact

        val ivImage: ImageView = findViewById(R.id.ivDetailImage)
        val tvName: TextView = findViewById(R.id.tvDetailName)
        val tvPhone: TextView = findViewById(R.id.tvDetailPhone)

        contact?.let {
            ivImage.setImageResource(it.imageResId)
            tvName.text = it.name
            tvPhone.text = it.phoneNumber
        }
    }
}
