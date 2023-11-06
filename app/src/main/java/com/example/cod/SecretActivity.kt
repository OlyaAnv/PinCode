package com.example.cod

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cod.databinding.SecretActivityBinding

const val EXTRA_PIN_CODE = "EXTRA_PIN_CODE"
const val EXTRA_IS_TOAST = "EXTRA_IS_TOAST"
const val INTERNET_RESURS = "http://ya.ru"
const val PHONE_NUMBER = "tel:1234567"

class SecretActivity : AppCompatActivity(R.layout.secret_activity) {

    private lateinit var binding: SecretActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecretActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Получение данных с мейн активити
        val text = intent.getStringExtra(EXTRA_PIN_CODE)
        binding.textView.text = getString(R.string.secret_str, text)

        initButtons()
    }

    private fun initButtons() {
        binding.apply {
            //Отправка данных назад
            btnNew.setOnClickListener {
                setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_IS_TOAST, true))
                finish()
            }
            btnOld.setOnClickListener {
                setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_IS_TOAST, false))
                finish()
            }
            btnBrowser.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(INTERNET_RESURS))
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    noSuitableAppToast()
                }

            }
            btnShare.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Привет, это пример использования Intent.ACTION_SEND на Kotlin!"
                )
                startActivity(Intent.createChooser(intent, "Поделиться через"))
            }
            btnMail.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
                    putExtra(Intent.EXTRA_SUBJECT, "Тема письма")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Привет, это письмо отправлено через Intent на Kotlin!"
                    )
                }

                if (emailIntent.resolveActivity(packageManager) != null) {
                    startActivity(emailIntent)
                } else {
                    noSuitableAppToast()
                }
            }
            btnCall.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse(PHONE_NUMBER))
                if (callIntent.resolveActivity(packageManager) != null) {
                    startActivity(callIntent)
                } else {
                    noSuitableAppToast()
                }
            }
            btnCamera.setOnClickListener {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(packageManager) != null) {
                    startActivity(takePictureIntent)
                } else {
                    noSuitableAppToast()
                }
            }
        }
    }

    private fun noSuitableAppToast() {
        Toast.makeText(this@SecretActivity, "Нет подходящего приложения", Toast.LENGTH_SHORT).show()
    }
}