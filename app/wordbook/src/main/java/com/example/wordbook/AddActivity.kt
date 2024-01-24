package com.example.wordbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.wordbook.databinding.ActivityAddBinding
import com.example.wordbook.room.WordDatabase
import com.google.android.material.chip.Chip

class AddActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityAddBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        binding.btnFinishAdd.setOnClickListener { addWord() }
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

    // 단어 추가하기
    private fun addWord() {
        val text = binding.inputtextWord.text.toString()
        val mean = binding.inputtextMean.text.toString()
        val type = findViewById<Chip>(binding.chipGroupType.checkedChipId).text.toString()

        Log.d("wordssss", "$text $mean $type")

        val word = Word(text, mean, type)

        Thread { // DB에 접근할 때 UI Thread 와는 별개로 작동해야 함 - 아니면 작동시간이 오래 걸려서 ANR이 발생할 수 있음

            // database의 instance에 접근
            WordDatabase.getInstance(this)
                ?.wordDao()?.insert(word) // 추가하기

            runOnUiThread { // WorkThread에서 접근하지 않도록 runOnUIThread 안에 넣기
                Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent().putExtra("isUpdated", true)
            setResult(RESULT_OK, intent) // Main Activity에서 registerForActivityResult()가 돌아옴을 들을 수 있음

            finish()
        }.start()
    }
}