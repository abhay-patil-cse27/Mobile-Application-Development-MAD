package com.example.font_controller_app

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvPreview: TextView
    private lateinit var etInput: EditText
    private lateinit var cbEnableEdit: CheckBox
    private lateinit var spinnerFontSize: Spinner
    private lateinit var rgColor: RadioGroup
    private lateinit var cbBold: CheckBox
    private lateinit var cbItalic: CheckBox
    private lateinit var lvFontFamily: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        tvPreview = findViewById(R.id.tvPreview)
        etInput = findViewById(R.id.etInput)
        cbEnableEdit = findViewById(R.id.cbEnableEdit)
        spinnerFontSize = findViewById(R.id.spinnerFontSize)
        rgColor = findViewById(R.id.rgColor)
        cbBold = findViewById(R.id.cbBold)
        cbItalic = findViewById(R.id.cbItalic)
        lvFontFamily = findViewById(R.id.lvFontFamily)

        setupEditText()
        setupEnableDisable()
        setupSpinner()
        setupRadioGroup()
        setupCheckBoxes()
        setupListView()
    }

    // 1. EditText: Update preview text dynamically
    private fun setupEditText() {
        etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvPreview.text = if (s.isNullOrEmpty()) "Preview Text" else s.toString()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // 2. Option Box: Enable/Disable EditText
    private fun setupEnableDisable() {
        cbEnableEdit.setOnCheckedChangeListener { _, isChecked ->
            etInput.isEnabled = isChecked
        }
    }

    // 3. Spinner: Change Font Size (View - Adapter - Data)
    private fun setupSpinner() {
        val sizes = resources.getStringArray(R.array.font_sizes) // Data (from XML)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sizes) // Adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFontSize.adapter = adapter // View

        spinnerFontSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSize = sizes[position].replace("sp", "").toFloat()
                tvPreview.textSize = selectedSize
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    // 4. RadioGroup: Change Text Color
    private fun setupRadioGroup() {
        rgColor.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbRed -> tvPreview.setTextColor(Color.RED)
                R.id.rbGreen -> tvPreview.setTextColor(Color.GREEN)
                R.id.rbBlue -> tvPreview.setTextColor(Color.BLUE)
            }
        }
    }

    // 5. CheckBoxes: Change Style (Bold/Italic)
    private fun setupCheckBoxes() {
        val updateStyle = {
            val style = when {
                cbBold.isChecked && cbItalic.isChecked -> Typeface.BOLD_ITALIC
                cbBold.isChecked -> Typeface.BOLD
                cbItalic.isChecked -> Typeface.ITALIC
                else -> Typeface.NORMAL
            }
            tvPreview.setTypeface(tvPreview.typeface, style)
        }

        cbBold.setOnCheckedChangeListener { _, _ -> updateStyle() }
        cbItalic.setOnCheckedChangeListener { _, _ -> updateStyle() }
    }

    // 6. ListView: Change Font Family (View - Adapter - Data)
    private fun setupListView() {
        val families = resources.getStringArray(R.array.font_families) // Data
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, families) // Adapter
        lvFontFamily.adapter = adapter // View

        lvFontFamily.setOnItemClickListener { _, _, position, _ ->
            val typeface = when (families[position]) {
                "Monospace" -> Typeface.MONOSPACE
                "Serif" -> Typeface.SERIF
                "Sans Serif" -> Typeface.SANS_SERIF
                else -> Typeface.DEFAULT
            }
            // Preserve bold/italic style while changing family
            val currentStyle = tvPreview.typeface?.style ?: Typeface.NORMAL
            tvPreview.setTypeface(typeface, currentStyle)
        }
    }
}
