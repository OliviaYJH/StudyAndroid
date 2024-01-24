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

    // command + shift + v => 그동안 clipboard 복사한 것 확인 가능
    private fun initViews() {
        val types = listOf("명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사")

        binding.chipGroupType.apply {
            types.forEach { type ->
                addView(createChip(type))
            }
        }
    }

    // chip 만들기
    private fun createChip(type: String): Chip {
        return Chip(this).apply {
            text = type
            isCheckable = true
            isClickable = true
        }
    }
}