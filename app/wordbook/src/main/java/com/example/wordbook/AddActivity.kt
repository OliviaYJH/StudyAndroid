package com.example.wordbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wordbook.databinding.ActivityAddBinding
import com.google.android.material.chip.Chip

class AddActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityAddBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    
}