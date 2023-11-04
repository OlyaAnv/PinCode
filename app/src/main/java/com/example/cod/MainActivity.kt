package com.example.cod

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.cod.databinding.ActivityMainBinding

private const val KEY_PINCODE = "PINCODE"
private const val KEY_COLOR = "COLOR"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var correctСode = "1567"
    private var pinCodeString = ""
    private var rightColor = Color.GREEN
    private var errorColor = Color.RED
    private var normalColor = Color.GRAY

    private val resultActivityResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val isToast = it.data?.getBooleanExtra(EXTRA_IS_TOAST, false) ?: false
                if (isToast)
                    Toast.makeText(this, getString(R.string.welcome_back), Toast.LENGTH_SHORT)
                        .show()
            }
            pinCodeString = ""
            showCodeOnScreen()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkBundle(savedInstanceState)
        initColors()
        initNumButtons()
        initOkButton()
        initDelButton()
    }

    private fun initColors() {
        rightColor = getColor(R.color.btn_ok)
        errorColor = getColor(R.color.wrong)
        normalColor = getColor(R.color.text_color)
    }

    private fun checkBundle(savedInstanceState: Bundle?) {
        savedInstanceState?.getInt(KEY_COLOR)?.let {
            binding.tvTitle.setTextColor(it)
        }
        savedInstanceState?.getString(KEY_PINCODE)?.let {
            pinCodeString = it
            showCodeOnScreen()
        }
    }

    private fun initDelButton() {
        binding.tvDel.setOnClickListener {
            if (pinCodeString.isNotEmpty()) {
                //codeString = codeString.substring(0, codeString.length - 1)
                pinCodeString = pinCodeString.dropLast(1)
                binding.tvTitle.setTextColor(normalColor)
                showCodeOnScreen()
            }
        }
    }

    private fun initOkButton() {
        binding.tvOk.setOnClickListener {
            if (pinCodeString.length >= correctСode.length)
                checkCode()
        }
    }

    private fun initNumButtons() {
        binding.tv1.setOnClickListener { setCode(1) }
        binding.tv2.setOnClickListener { setCode(2) }
        binding.tv3.setOnClickListener { setCode(3) }
        binding.tv4.setOnClickListener { setCode(4) }
        binding.tv5.setOnClickListener { setCode(5) }
        binding.tv6.setOnClickListener { setCode(6) }
        binding.tv7.setOnClickListener { setCode(7) }
        binding.tv8.setOnClickListener { setCode(8) }
        binding.tv9.setOnClickListener { setCode(9) }
        binding.tv0.setOnClickListener { setCode(0) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_PINCODE, pinCodeString)
        outState.putInt(KEY_COLOR, binding.tvTitle.currentTextColor)
    }

    private fun isCodeCorrect(): Boolean = (pinCodeString == correctСode)

    private fun setCode(number: Int) {
        if (pinCodeString.length < correctСode.length) {
            pinCodeString += number.toString()
            showCodeOnScreen()
        }
    }

    private fun checkCode() {
        if (isCodeCorrect()) {
            Intent(this, SecretActivity::class.java).let {
                it.putExtra(EXTRA_PIN_CODE, pinCodeString)
                resultActivityResult.launch(it)
            }
            binding.tvTitle.setTextColor(rightColor)
        } else {
            binding.tvTitle.setTextColor(errorColor)
        }
        showCodeOnScreen()
    }

    private fun showCodeOnScreen() {
        binding.tvTitle.text = pinCodeString
    }
}