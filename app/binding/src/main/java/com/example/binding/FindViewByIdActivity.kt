package com.example.binding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class FindViewByIdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txt1 = findViewById<TextView>(R.id.tv_1)
    }

}