package com.example.webtoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.webtoon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            supportFragmentManager.beginTransaction().apply { // 작업 시작
                replace(R.id.fl_container, WebViewFragment()) // WebViewFragment를 항상 새로 생성함
                commit() // 작업 끝남
            }

        }
        binding.btn2.setOnClickListener {
            supportFragmentManager.beginTransaction().apply { // 작업 시작
                replace(R.id.fl_container, BFragment())
                commit() // 작업 끝남
            }

        }
    }
}