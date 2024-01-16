package com.example.lifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener { navigateNextActivity() }
        findViewById<Button>(R.id.button2).setOnClickListener { finish() }

        Toast.makeText(this, "라이프사이클 - Main : onCreate", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Main", "onCreate")

        findViewById<TextView>(R.id.textView).setOnClickListener { (it as TextView).setTextColor((resources.getColor(R.color.purple_200, null)))  }
        findViewById<TextView>(R.id.textView).setOnLongClickListener {
            (it as TextView).text = "test"
            true
        }
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "라이프사이클 - Main : onStart", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Main", "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "라이프사이클 - Main : onRestart", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Main", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "라이프사이클 - Main : onResume", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Main", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "라이프사이클 - Main : onPause", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Main", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "라이프사이클 - Main : onStop", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Main", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "라이프사이클 - Main : onDestroy", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Main", "onDestroy")
    }

    private fun navigateNextActivity() {
        val intent = Intent(this, NextActivity::class.java)
        startActivity(intent)
    }

}