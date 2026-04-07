package com.example.duplicate_character_reduction_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etInput: EditText
    private lateinit var tvOriginal: TextView
    private lateinit var tvResult: TextView
    private lateinit var tvSteps: TextView
    private lateinit var btnProcess: Button
    private lateinit var btnClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInput    = findViewById(R.id.etInput)
        tvOriginal = findViewById(R.id.tvOriginal)
        tvResult   = findViewById(R.id.tvResult)
        tvSteps    = findViewById(R.id.tvSteps)
        btnProcess = findViewById(R.id.btnProcess)
        btnClear   = findViewById(R.id.btnClear)

        btnProcess.setOnClickListener {
            val input = etInput.text.toString()

            if (input.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_empty_input), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            tvOriginal.text = getString(R.string.label_original, input)

            val (result, steps) = removeDuplicates(input)

            val resultDisplay = result.ifEmpty { getString(R.string.empty_result_message) }
            tvResult.text = getString(R.string.label_result, resultDisplay)
            tvSteps.text  = getString(R.string.label_steps_with_content, steps)
        }

        btnClear.setOnClickListener {
            etInput.text.clear()
            val dash = getString(R.string.placeholder_dash)
            tvOriginal.text = getString(R.string.label_original, dash)
            tvResult.text   = getString(R.string.label_result, dash)
            tvSteps.text    = dash
        }
    }

    // ── Core logic using ArrayDeque as a Stack ───────────────────────────────
    // Chain reaction: keep popping as long as top == current char
    // e.g. "aabccba" → removes aa → "bccb" → removes cc → "bb" → removes bb → ""

    private fun removeDuplicates(input: String): Pair<String, String> {
        val stack = ArrayDeque<Char>()   // used as a stack (addLast / removeLast)
        val log   = StringBuilder()
        var step  = 1

        for (ch in input) {
            if (stack.isNotEmpty() && stack.last() == ch) {
                // Top matches — pop it (chain reaction removal)
                stack.removeLast()
                log.appendLine("$step. '$ch' matched top → popped  | Stack: [${stack.joinToString("")}]")
            } else {
                // No match — push onto stack
                stack.addLast(ch)
                log.appendLine("$step. '$ch' pushed               | Stack: [${stack.joinToString("")}]")
            }
            step++
        }

        val result = stack.joinToString("")
        return Pair(result, log.toString())
    }
}