package com.example.cod

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_PIN_CODE = "EXTRA_PIN_CODE"
const val EXTRA_IS_TOAST = "EXTRA_IS_TOAST"

class SecretActivity : AppCompatActivity(R.layout.secret_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Получение данных с мейн активити
        val text = intent.getStringExtra(EXTRA_PIN_CODE)
        findViewById<TextView>(R.id.textView).text = getString(R.string.secret_str, text)

        //Отправка данных назад
        findViewById<Button>(R.id.btnNew).setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_IS_TOAST, true))
            finish()
        }
        findViewById<Button>(R.id.btnOld).setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_IS_TOAST, false))
            finish()
        }
    }
}