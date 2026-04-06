package com.example.mycalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView

    private var currentInput: String = ""
    private var firstOperand: Double = 0.0
    private var operator: String = ""
    private var isNewInput: Boolean = true
    private var justCalculated: Boolean = false  // tracks if = was just pressed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)

        // Number buttons
        findViewById<Button>(R.id.btn0).setOnClickListener { appendDigit("0") }
        findViewById<Button>(R.id.btn1).setOnClickListener { appendDigit("1") }
        findViewById<Button>(R.id.btn2).setOnClickListener { appendDigit("2") }
        findViewById<Button>(R.id.btn3).setOnClickListener { appendDigit("3") }
        findViewById<Button>(R.id.btn4).setOnClickListener { appendDigit("4") }
        findViewById<Button>(R.id.btn5).setOnClickListener { appendDigit("5") }
        findViewById<Button>(R.id.btn6).setOnClickListener { appendDigit("6") }
        findViewById<Button>(R.id.btn7).setOnClickListener { appendDigit("7") }
        findViewById<Button>(R.id.btn8).setOnClickListener { appendDigit("8") }
        findViewById<Button>(R.id.btn9).setOnClickListener { appendDigit("9") }

        // Decimal
        findViewById<Button>(R.id.btnDot).setOnClickListener { appendDot() }

        // Operators
        findViewById<Button>(R.id.btnAdd).setOnClickListener      { setOperator("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { setOperator("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener   { setOperator("/") }

        // Actions
        findViewById<Button>(R.id.btnEquals).setOnClickListener     { calculateResult() }
        findViewById<Button>(R.id.btnClear).setOnClickListener      { clearAll() }
        findViewById<Button>(R.id.btnToggleSign).setOnClickListener { toggleSign() }
        findViewById<Button>(R.id.btnPercent).setOnClickListener    { applyPercent() }
    }

    private fun appendDigit(digit: String) {
        if (isNewInput) {
            currentInput = digit
            isNewInput = false
            justCalculated = false
        } else {
            currentInput = if (currentInput == "0") digit else currentInput + digit
        }
        tvDisplay.text = currentInput
    }

    private fun appendDot() {
        if (isNewInput) {
            currentInput = "0."
            isNewInput = false
            justCalculated = false
        } else if (!currentInput.contains(".")) {
            currentInput += "."
        }
        tvDisplay.text = currentInput
    }

    private fun setOperator(op: String) {
        // If = was just pressed, chain from the result already in currentInput
        // If mid-expression, compute intermediate result first
        if (!isNewInput && operator.isNotEmpty()) {
            calculateResult()   // compute intermediate, result lands in currentInput
        }

        if (currentInput.isNotEmpty()) {
            firstOperand = currentInput.toDouble()
        }
        operator = op
        isNewInput = true
        justCalculated = false
    }

    @SuppressLint("SetTextI18n")
    private fun calculateResult() {
        if (operator.isEmpty() || currentInput.isEmpty()) return

        val secondOperand = currentInput.toDouble()

        val result: Double = when (operator) {
            "+" -> firstOperand + secondOperand
            "-" -> firstOperand - secondOperand
            "*" -> firstOperand * secondOperand
            "/" -> {
                if (secondOperand != 0.0) {
                    firstOperand / secondOperand
                } else {
                    tvDisplay.text = "Error"
                    resetState()
                    return
                }
            }
            else -> return
        }

        currentInput = formatResult(result)
        tvDisplay.text = currentInput
        firstOperand = result   // allow chaining after =
        operator = ""
        isNewInput = true
        justCalculated = true
    }

    private fun formatResult(value: Double): String {
        return if (value == value.toLong().toDouble())
            value.toLong().toString()
        else
            value.toString()
    }

    private fun clearAll() {
        currentInput = ""
        firstOperand = 0.0
        operator = ""
        isNewInput = true
        justCalculated = false
        tvDisplay.text = "0"
    }

    private fun toggleSign() {
        if (currentInput.isEmpty() || currentInput == "0") return
        currentInput = if (currentInput.startsWith("-"))
            currentInput.removePrefix("-")
        else
            "-$currentInput"
        tvDisplay.text = currentInput
    }

    private fun applyPercent() {
        if (currentInput.isEmpty()) return
        val value = currentInput.toDouble() / 100
        currentInput = formatResult(value)
        tvDisplay.text = currentInput
    }

    private fun resetState() {
        currentInput = ""
        firstOperand = 0.0
        operator = ""
        isNewInput = true
        justCalculated = false
    }
}