package com.example.quicksort_threadapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etInput: EditText
    private lateinit var tvBefore: TextView
    private lateinit var tvAfter: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnSort: Button
    private lateinit var btnClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInput  = findViewById(R.id.etInput)
        tvBefore = findViewById(R.id.tvBefore)
        tvAfter  = findViewById(R.id.tvAfter)
        tvStatus = findViewById(R.id.tvStatus)
        btnSort  = findViewById(R.id.btnSort)
        btnClear = findViewById(R.id.btnClear)

        // Set roll info from strings.xml
        findViewById<TextView>(R.id.tvRollInfo).text = getString(R.string.roll_info)

        val mainHandler = Handler(Looper.getMainLooper())

        btnSort.setOnClickListener {
            val input = etInput.text.toString().trim()

            if (input.isEmpty()) {
                Toast.makeText(this, R.string.empty_input, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Parse input — split by comma, strip spaces, convert to Int
            val arr: IntArray
            try {
                arr = input.split(",")
                    .filter { it.isNotBlank() }
                    .map { it.trim().toInt() }
                    .toIntArray()
            } catch (e: Exception) {
                Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            tvBefore.text = getString(R.string.before_sorting, arr.joinToString(", "))
            tvAfter.text  = getString(R.string.after_sorting, getString(R.string.empty_dash))
            tvStatus.text = getString(R.string.status_sorting)
            btnSort.isEnabled = false

            val thread = Thread {
                quickSort(arr, 0, arr.size - 1)

                mainHandler.post {
                    tvAfter.text  = getString(R.string.after_sorting, arr.joinToString(", "))
                    tvStatus.text = getString(R.string.status_done)
                    btnSort.isEnabled = true
                }
            }

            thread.start()
        }

        btnClear.setOnClickListener {
            etInput.text.clear()
            tvBefore.text = getString(R.string.before_sorting, getString(R.string.empty_dash))
            tvAfter.text  = getString(R.string.after_sorting, getString(R.string.empty_dash))
            tvStatus.text = getString(R.string.status_ready)
        }
    }

    private fun quickSort(arr: IntArray, low: Int, high: Int) {
        if (low < high) {
            val pivot = arr[high]
            var i = low - 1

            for (j in low until high) {
                if (arr[j] < pivot) {
                    i++
                    val temp = arr[i]
                    arr[i]   = arr[j]
                    arr[j]   = temp
                }
            }

            val temp = arr[i + 1]
            arr[i + 1] = arr[high]
            arr[high]  = temp

            val p = i + 1
            quickSort(arr, low, p - 1)
            quickSort(arr, p + 1, high)
        }
    }
}