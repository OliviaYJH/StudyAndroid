package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlay.setOnClickListener { mediaPlayerPlay() }
        binding.btnPause.setOnClickListener { mediaPlayerPause() }
        binding.btnStop.setOnClickListener { mediaPlayerStop() }
    }

    private fun mediaPlayerPlay() {
        Log.i("mediaPlayer", "start")
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.temp).apply {
                isLooping = true // 계속 반복
            }
        }
        mediaPlayer?.start()
    }

    private fun mediaPlayerPause() {
        mediaPlayer?.pause()
    }

    private fun mediaPlayerStop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}