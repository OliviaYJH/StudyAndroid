package com.example.musicplayer

import android.app.*
import android.content.Intent
import android.graphics.drawable.Icon
import android.media.MediaPlayer
import android.os.IBinder

class MediaPlayerService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        // 화면에 사용할 아이콘들
        val playIcon = Icon.createWithResource(baseContext, R.drawable.ic_play)
        val stopIcon = Icon.createWithResource(baseContext, R.drawable.ic_stop)
        val pauseIcon = Icon.createWithResource(baseContext, R.drawable.ic_pause)

        val mainPendingIntent = PendingIntent.getActivity(
            baseContext,
            0,
            Intent(baseContext, MainActivity::class.java)
                .apply {
                       flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                       // 기존의 생성된 activity들이 있다면 기존읙 것들을 재사용함
                },
            PendingIntent.FLAG_IMMUTABLE
        )
        val pausePendingIntent = PendingIntent.getService(
            baseContext,
            0,
            Intent(baseContext, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_PAUSE },
            PendingIntent.FLAG_IMMUTABLE // flag 넣기 - 더 이상 변경하지 않겠다
        )
        val playPendingIntent = PendingIntent.getService(
            baseContext,
            0,
            Intent(baseContext, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_PLAY },
            PendingIntent.FLAG_IMMUTABLE
        )
        val stopPendingIntent = PendingIntent.getService(
            baseContext,
            0,
            Intent(baseContext, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_STOP },
            PendingIntent.FLAG_IMMUTABLE
        )


        // Notification 생성
        val notification = Notification.Builder(baseContext, CHANNEL_ID)
            // 관련 내용 설정
            .setStyle(
                Notification.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2) // 각각에 맞는 action이 들어가게 됨
            )
            .setVisibility(Notification.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_star)
            .addAction(
                Notification.Action.Builder(
                    // 일시정지
                    pauseIcon,
                    "Pause", // title
                    pausePendingIntent
                ).build()
            )
            .addAction(
                Notification.Action.Builder(
                    playIcon,
                    "Play",
                    playPendingIntent
                ).build()
            )
            .addAction(
                Notification.Action.Builder(
                    stopIcon,
                    "Stop",
                    stopPendingIntent
                ).build()
            )
            .setContentIntent(mainPendingIntent) // Notification 자체를 클릭했을 때의 실행
            .setContentTitle("음악 재생")
            .setContentText("음원이 재생 중 입니다..")
            .build()

        
        startForeground(100, notification)
    }

    // Notification channel 생성
    private fun createNotificationChannel() {
        val channelId = CHANNEL_ID
        val channel = NotificationChannel(channelId, "MEDIA_PLAYER", NotificationManager.IMPORTANCE_DEFAULT)

        val notificationManager = baseContext.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
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