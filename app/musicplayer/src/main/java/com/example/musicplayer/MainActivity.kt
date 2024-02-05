package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlay.setOnClickListener { mediaPlayerPlay() }
        binding.btnPause.setOnClickListener { mediaPlayerPause() }
        binding.btnStop.setOnClickListener { mediaPlayerStop() }
    }

    private fun mediaPlayerPlay() {

    }

    private fun mediaPlayerPause() {

    }

    private fun mediaPlayerStop() {
        
    }
}