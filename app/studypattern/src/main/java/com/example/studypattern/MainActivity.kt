package com.example.studypattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studypattern.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // dataBinding 사용
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            it.view = this
        }
    }

    fun openMVC() {

    }

    fun openMVP() {

    }

    fun openMVVM() {

    }

    fun openMVI() {

    }
}