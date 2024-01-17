package com.example.countnum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.countnum.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var number = 0
        binding.btnReset.setOnClickListener {
            number = 0
            binding.tvNumber.text = number.toString()
        }

        binding.btnPlus.setOnClickListener {
            number++
            binding.tvNumber.text = number.toString()
        }
    }
}