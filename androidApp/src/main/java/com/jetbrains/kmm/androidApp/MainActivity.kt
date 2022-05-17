package com.jetbrains.kmm.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.jetbrains.kmm.shared.Greeting
import com.jetbrains.kmm.shared.Calculator
import android.widget.TextView
import io.sentry.Sentry

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.textView)
        tv.text = greet()

        val numATV: EditText = findViewById(R.id.editTextNumberDecimalA)
        val numBTV: EditText = findViewById(R.id.editTextNumberDecimalB)

        val sumTV: TextView = findViewById(R.id.textViewSum)

        val textWatcher = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    val numA = Integer.parseInt(numATV.text.toString())
                    val numB = Integer.parseInt(numBTV.text.toString())
                    sumTV.text =  "= " + Calculator.sum(numA, numB).toString()
                } catch(e: NumberFormatException) {
                    sumTV.text = "= 🤔"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        numATV.addTextChangedListener(textWatcher)
        numBTV.addTextChangedListener(textWatcher)

        val uncaught: Button = findViewById(R.id.uncaught)
        uncaught.setOnClickListener {
            run {
                throw RuntimeException ("uncaught exception")
            }
        }

        val caught: Button = findViewById(R.id.caught)
        caught.setOnClickListener {
            run {
                Sentry.captureException(RuntimeException("caught exception"))
            }
        }

        val spawnThread: Button = findViewById(R.id.spawn)
        spawnThread.setOnClickListener {
            run{
                Thread(ToastMe(this)).start()
            }
        }

        val sendThreadMessage: Button = findViewById(R.id.thread)
        sendThreadMessage.setOnClickListener{
            run {
                Sentry.captureMessage("this is a message with thread information")
            }
        }
    }
}
