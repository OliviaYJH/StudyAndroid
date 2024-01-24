package com.example.wordbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    private lateinit var wordAdapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        navigateToAdd()
    }

    private fun initRecyclerView() {
        //val dummyList = makeDummyList()

        wordAdapter = WordAdapter(mutableListOf(), this)
        // scope function 사용
        binding.rvWord.apply {
            // recyclerView에 adapter 연결
            adapter = wordAdapter

            // layoutManager 지정 - 어떤 방향으로 구성
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            // divider 넣기
            val dividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun makeDummyList(): MutableList<Word> {
        return mutableListOf(
            Word("weather", "날씨", "명사"),
            Word("run", "달리다", "동사"),
            Word("honey", "꿀", "명사")
        )
    }

    private fun navigateToAdd() {
        binding.btnAdd.setOnClickListener {
            Intent(this, AddActivity::class.java).let {
                startActivity(it)
            }
        }
    }

    // click listener
    override fun onClick(word: Word) {
        Toast.makeText(this, "${word.word} 가 클릭 됐습니다", Toast.LENGTH_SHORT).show()
    }
}