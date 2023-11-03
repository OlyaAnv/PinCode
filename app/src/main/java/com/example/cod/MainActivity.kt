package com.example.cod

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cod.databinding.ActivityMainBinding

private const val CORRECT_CODE_STRING = "1567"
private const val CODE_LENGTH = 4

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var codeString = ""
    private var rightColor = Color.GREEN
    private var errorColor = Color.RED
    private var normalColor = Color.GRAY

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
        if (savedInstanceState != null) {
            binding.tvTitle.setTextColor(savedInstanceState.getInt("colorKey"))
            codeString = savedInstanceState.getString("stringKey") ?: ""

            if (codeString.isNotEmpty())
                showCodeOnScreen()
//            else
//                showTitleOnScreen()
        }
    }

    private fun initDelButton() {
        binding.tvDel.setOnClickListener {
            if (codeString.isNotEmpty()) {
                //codeString = codeString.substring(0, codeString.length - 1)
                codeString = codeString.dropLast(1)
                binding.tvTitle.setTextColor(normalColor)
                showCodeOnScreen()
            }
        }
    }

    private fun initOkButton() {
        binding.tvOk.setOnClickListener {
            if (codeString.length >= CODE_LENGTH)
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
        outState.putString("stringKey", codeString)
        outState.putInt("colorKey", binding.tvTitle.currentTextColor)
    }

    private fun isCodeCorrect(): Boolean = (codeString == CORRECT_CODE_STRING)

    private fun setCode(number: Int) {
        if (codeString.length < CODE_LENGTH) {
            codeString += number.toString()
            showCodeOnScreen()
        }
    }

    private fun checkCode() {
        if (isCodeCorrect()) {
            Toast.makeText(this, getString(R.string.correct_answer), Toast.LENGTH_SHORT).show()
            binding.tvTitle.setTextColor(rightColor)
        } else {
            binding.tvTitle.setTextColor(errorColor)
        }
        showCodeOnScreen()
    }

    private fun showCodeOnScreen() {
        binding.tvTitle.text = codeString
    }
}