package com.example.font_controller_app

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etInput: EditText
    private lateinit var tvPreview: TextView
    private lateinit var rgColor: RadioGroup
    private lateinit var cbBold: CheckBox
    private lateinit var cbItalic: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        etInput = findViewById(R.id.etInput)
        tvPreview = findViewById(R.id.tvPreview)
        rgColor = findViewById(R.id.rgColor)
        cbBold = findViewById(R.id.cbBold)
        cbItalic = findViewById(R.id.cbItalic)

        // 1. EditText Logic: Update preview text as user types
        etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvPreview.text = if (s.isNullOrEmpty()) {
                    getString(R.string.preview_text_default)
                } else {
                    s.toString()
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // 2. RadioButton Logic: Change text color
        rgColor.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbRed -> tvPreview.setTextColor(Color.RED)
                R.id.rbGreen -> tvPreview.setTextColor(Color.GREEN)
                R.id.rbBlue -> tvPreview.setTextColor(Color.BLUE)
            }
        }

        // 3. CheckBox Logic: Change font style (Bold/Italic)
        cbBold.setOnCheckedChangeListener { _, _ -> updateTextStyle() }
        cbItalic.setOnCheckedChangeListener { _, _ -> updateTextStyle() }
    }

    private fun updateTextStyle() {
        val isBold = cbBold.isChecked
        val isItalic = cbItalic.isChecked

        val style = when {
            isBold && isItalic -> Typeface.BOLD_ITALIC
            isBold -> Typeface.BOLD
            isItalic -> Typeface.ITALIC
            else -> Typeface.NORMAL
        }
        tvPreview.setTypeface(null, style)
    }
}
