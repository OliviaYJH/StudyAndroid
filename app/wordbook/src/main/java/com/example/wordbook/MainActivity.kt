package com.example.wordbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordbook.databinding.ActivityMainBinding
import com.example.wordbook.room.WordDatabase

class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    private lateinit var wordAdapter: WordAdapter
    private var selectedWord: Word? = null
    private val updateAddWordResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // 돌아오면 화면 update
        val isUpdated = result.data?.getBooleanExtra("isUpdated", false) ?: false
        if (result.resultCode == RESULT_OK && isUpdated) {
            updateAddWord()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        navigateToAdd()
        deleteWord()
    }

    private fun initRecyclerView() {
        //val dummyList = makeDummyList()

        wordAdapter = WordAdapter(mutableListOf(), this)
        // scope function 사용
        binding.rvWord.apply {
            // recyclerView에 adapter 연결
            adapter = wordAdapter

            // layoutManager 지정 - 어떤 방향으로 구성
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            // divider 넣기
            val dividerItemDecoration =
                DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        // 저장한 data 띄우기
        Thread {
            wordAdapter.list.addAll(getData())

            runOnUiThread {
                wordAdapter.notifyDataSetChanged() // 추가나 삭제가 되면 UI가 변경되기 때문
                /*
                notifyDataSetChanged() - 부하가 많이 걸림
                 */
            }
        }.start()
    }

    // data 가져오기
    private fun getData(): List<Word> =
        WordDatabase.getInstance(this)?.wordDao()?.getAll() ?: emptyList()


    private fun makeDummyList(): MutableList<Word> {
        return mutableListOf(
            Word("weather", "날씨", "명사"),
            Word("run", "달리다", "동사"),
            Word("honey", "꿀", "명사")
        )
    }

    private fun navigateToAdd() {
        binding.btnAdd.setOnClickListener {
            /*
            EditActivity에서 실행 이후에 setResult()를 설정하면
            이후에 다시 MainActivity에 돌아오면 들을 수 있음
            => registerForActivityResult() 사용
             */


            Intent(this, AddActivity::class.java).let {
                updateAddWordResult.launch(it)
            }
        }
    }

    // 리스트에 추가하기
    private fun updateAddWord() {
        Thread {
            WordDatabase.getInstance(this)?.wordDao()?.getLatestWord()?.let { word ->
                wordAdapter.list.add(0, word)

                // data가 추가되었음을 알림
                runOnUiThread {
                    wordAdapter.notifyDataSetChanged()
                }
            }
        }.start()
    }

    private fun deleteWord() {
        binding.btnDelete.setOnClickListener {
            if (selectedWord == null) return@setOnClickListener


            Thread {
                selectedWord?.let { word ->
                    WordDatabase.getInstance(this)?.wordDao()?.delete(word)
                    runOnUiThread {
                        wordAdapter.list.remove(word)
                        wordAdapter.notifyDataSetChanged()

                        binding.tvWord.text = ""
                        binding.tvMean.text = ""

                        Toast.makeText(this, "삭제 완료", Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }
    }

    // click listener
    override fun onClick(word: Word) {
        selectedWord = word
        binding.tvWord.text = word.word
        binding.tvMean.text = word.mean

        Toast.makeText(this, "${word.word} 가 클릭 됐습니다", Toast.LENGTH_SHORT).show()
    }
}