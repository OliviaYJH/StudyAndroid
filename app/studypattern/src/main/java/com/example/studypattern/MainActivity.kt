package com.example.studypattern

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studypattern.databinding.ActivityMainBinding
import com.example.studypattern.mvc.MVCActivity
import com.example.studypattern.mvp.MVPActivity

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
        // Activity와 Presenter는 항상 1:1 구조!
        startActivity(Intent(this, MVPActivity::class.java))
    }

    fun openMVVM() {

    }

    fun openMVI() {

    }
}