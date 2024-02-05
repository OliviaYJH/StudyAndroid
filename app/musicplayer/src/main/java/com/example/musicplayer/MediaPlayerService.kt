package com.example.musicplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MediaPlayerService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // service가 실행되고 onCreate가 된 다음 바로 불리는 곳
        when (intent?.action) {
            MEDIA_PLAYER_PLAY -> {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(baseContext, R.raw.temp)
                }
                mediaPlayer?.start()
            }

            MEDIA_PLAYER_PAUSE -> {
                mediaPlayer?.pause()
            }

            MEDIA_PLAYER_STOP -> {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                stopSelf() // service 종료: 여러 음원을 실행시킬 수도 있기 때문, 안하면 계속 background에서 돌고 있음
            }
        }

        return super.onStartCommand(intent, flags, startId) // return 값으로 integer값 반환
    }
}