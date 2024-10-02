package com.example.myapplication


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var operand1: Double? = null
    private var operand2: Double? = null
    private var operator: String? = null
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Змініть на ваш layout файл

        display = findViewById(R.id.display)

        // Прив'язка кнопок
        val buttons = mapOf(
            R.id.button_0 to "0", R.id.button_7 to "1", R.id.button_8 to "2", R.id.button_9 to "3",
            R.id.button_4 to "4", R.id.button_5 to "5", R.id.button_6 to "6",
            R.id.button_1 to "7", R.id.button_2 to "8", R.id.button_3 to "9",
            R.id.button_ac to "AC", R.id.button_plus_minus to "+/-", R.id.button_percent to "%",
            R.id.button_divide to "/", R.id.button_multiply to "x", R.id.button_minus to "-",
            R.id.button_plus to "+"
        )

        for ((id, value) in buttons) {
            findViewById<Button>(id).setOnClickListener { onButtonClick(value) }
        }

        findViewById<Button>(R.id.button_equals).setOnClickListener { calculateResult() }
    }

    private fun onButtonClick(value: String) {
        when (value) {
            "AC" -> clear()
            "+/-" -> toggleSign()
            "+", "-", "x", "/", "%" -> {
                if (display.text.isNotEmpty()) {
                    operand1 = display.text.toString().toDouble()
                    operator = value
                    isNewOperation = true
                }
            }
            else -> {
                if (isNewOperation) {
                    display.text = ""
                    isNewOperation = false
                }
                display.append(value)
            }
        }
    }

    private fun calculateResult() {
        val input = display.text.toString()
        if (input.isNotEmpty()) {
            operand2 = input.toDouble()
            if (operand1 != null && operator != null && operand2 != null) {
                val result: Any = when (operator) {
                    "+" -> (operand1!! + operand2!!)
                    "-" -> (operand1!! - operand2!!)
                    "x" -> (operand1!! * operand2!!)
                    "/" -> if (operand2!! != 0.0) (operand1!! / operand2!!) else "Error"
                    "%" -> (operand1!! * operand2!! / 100)
                    else -> 0.0
                }
                display.text = result.toString()
                operand1 = when (result) {
                    is Double -> result // Зберігаємо результат як перший операнд для наступної операції
                    else -> null
                }
                operator = null // Скидаємо оператор
            }
            isNewOperation = true
        }
    }

    private fun toggleSign() {
        val input = display.text.toString()
        if (input.isNotEmpty()) {
            val number = input.toDouble()
            display.text = (-number).toString()
        }
    }

    private fun clear() {
        display.text = "0"
        operand1 = null
        operand2 = null
        operator = null
        isNewOperation = true
    }
}
