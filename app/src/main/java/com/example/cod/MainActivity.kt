package com.example.cod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cod.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val correctCodeString = "1567"
    private val codeLength = 4
    private var codeString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            binding.tvTitle.setTextColor(savedInstanceState.getInt("colorKey"))
            codeString = savedInstanceState.getString("stringKey") ?: ""

            if (codeString.isNotEmpty())
                showCodeOnScreen()
            else
                showTitleOnScreen()
        }

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

        binding.tvOk.setOnClickListener {
            if (codeString.length >= codeLength)
                checkCode()
        }

        binding.tvDel.setOnClickListener {
            if (codeString.length > 1) {
                codeString = codeString.substring(0, codeString.length - 1)
                binding.tvTitle.setTextColor(getColor(R.color.text_color))
                showCodeOnScreen()
            } else if (codeString.length == 1) {
                codeString = ""
                binding.tvTitle.setTextColor(getColor(R.color.text_color))
                showTitleOnScreen()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("stringKey", codeString)
        outState.putInt("colorKey", binding.tvTitle.currentTextColor)
    }

    private fun isCodeCorrect(): Boolean = (codeString == correctCodeString)

    private fun setCode(number: Int) {
        if (codeString.length < codeLength) {
            codeString += number.toString()
            showCodeOnScreen()
        }
    }

    private fun checkCode() {
        if (isCodeCorrect()) {
            Toast.makeText(this, getString(R.string.correct_answer), Toast.LENGTH_SHORT).show()
            binding.tvTitle.setTextColor(getColor(R.color.btn_ok))
            showCodeOnScreen()
        } else {
            binding.tvTitle.setTextColor(getColor(R.color.wrong))
            showCodeOnScreen()
        }
    }

    private fun showCodeOnScreen() {
        binding.tvTitle.text = codeString
    }

    private fun showTitleOnScreen() {
        binding.tvTitle.text = getString(R.string.enter_code)
    }
}