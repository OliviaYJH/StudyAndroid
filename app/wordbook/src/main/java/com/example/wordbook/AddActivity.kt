package com.example.wordbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.UserDictionary.Words.addWord
import android.util.Log
import android.widget.Toast
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import com.example.wordbook.databinding.ActivityAddBinding
import com.example.wordbook.room.WordDatabase
import com.google.android.material.chip.Chip

class AddActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityAddBinding
    private val binding get() = _binding

    private var originData: Word? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        binding.btnFinishAdd.setOnClickListener {
            if (originData == null) addWord()
            else edit()
        }
    }

    // command + shift + v => 그동안 clipboard 복사한 것 확인 가능
    private fun initViews() {
        val types = listOf("명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사")

        binding.chipGroupType.apply {
            types.forEach { type ->
                addView(createChip(type))
            }
        }

        binding.inputtextWord.addTextChangedListener {
            it?.let { text ->
                binding.inputlayoutWord.error = when(text.length) {
                    0 -> "값을 입력해주세요"
                    1 -> "2자 이상을 입력해주세요"
                    else -> null
                }
            }
        }

        originData = intent.getParcelableExtra("originData")
        originData?.let { word ->
            binding.inputtextWord.setText(word.word)
            binding.inputtextMean.setText(word.mean)

            val selectedChip =
                binding.chipGroupType.children.firstOrNull { (it as Chip).text == word.type } as? Chip
            selectedChip?.isChecked = true
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

        val word = Word(text, mean, type)

        Thread { // DB에 접근할 때 UI Thread 와는 별개로 작동해야 함 - 아니면 작동시간이 오래 걸려서 ANR이 발생할 수 있음

            // database의 instance에 접근
            WordDatabase.getInstance(this)
                ?.wordDao()?.insert(word) // 추가하기

            runOnUiThread { // WorkThread에서 접근하지 않도록 runOnUIThread 안에 넣기
                Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent().putExtra("isUpdated", true)
            setResult(
                RESULT_OK,
                intent
            ) // Main Activity에서 registerForActivityResult()가 돌아옴을 들을 수 있음

            finish()
        }.start()
    }

    private fun edit() {
        val text = binding.inputtextWord.text.toString()
        val mean = binding.inputtextMean.text.toString()
        val type = findViewById<Chip>(binding.chipGroupType.checkedChipId).text.toString()

        val editWord = originData?.copy(text, mean, type)

        Thread {
            editWord?.let { // word가 nullable하기 때문
                WordDatabase.getInstance(this)?.wordDao()?.update(editWord)

                val intent = Intent().putExtra("editWord", editWord)
                setResult(RESULT_OK, intent)
                runOnUiThread {
                    Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show()
                }

                finish()
            }
        }.start()
    }
}