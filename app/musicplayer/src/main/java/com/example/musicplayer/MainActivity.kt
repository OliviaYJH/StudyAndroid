package com.example.musicplayer

import android.content.Intent
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
        // service 시작
        val intent = Intent(this, MediaPlayerService::class.java)
            .apply { action = MEDIA_PLAYER_PLAY }
        startService(intent)
    }

    private fun mediaPlayerPause() {
        val intent = Intent(this, MediaPlayerService::class.java)
            .apply { action = MEDIA_PLAYER_PAUSE }
        startService(intent)
    }

    private fun mediaPlayerStop() {
        val intent = Intent(this, MediaPlayerService::class.java)
            .apply { action = MEDIA_PLAYER_STOP }
        startService(intent)
    }
}