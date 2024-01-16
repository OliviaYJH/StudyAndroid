package com.example.binding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.binding.databinding.ActivityViewBindingBinding

class ViewBindingActivity : AppCompatActivity() {
    private var _binding: ActivityViewBindingBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityViewBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvViewBinding.text = "success"
    }
}