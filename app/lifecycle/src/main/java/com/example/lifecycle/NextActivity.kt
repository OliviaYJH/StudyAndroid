package com.example.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class NextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        Toast.makeText(this, "라이프사이클 - Next : onCreate", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Next", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "라이프사이클 - Next : onStart", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Next", "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "라이프사이클 - Next : onRestart", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Next", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "라이프사이클 - Next : onResume", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Next", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "라이프사이클 - Next : onPause", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Next", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "라이프사이클 - Next : onStop", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Next", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "라이프사이클 - Next : onDestroy", Toast.LENGTH_SHORT).show()
        Log.d("라이프사이클 - Next", "onDestroy")
    }
}