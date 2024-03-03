package com.example.studypattern

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studypattern.databinding.ActivityMainBinding
import com.example.studypattern.mvc.MVCActivity

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
        startActivity(Intent(this, MVCActivity::class.java))
    }

    fun openMVP() {

    }

    fun openMVVM() {

    }

    fun openMVI() {

    }
}