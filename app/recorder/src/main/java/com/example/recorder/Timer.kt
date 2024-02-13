package com.example.recorder

import android.os.Handler
import android.os.Looper

class Timer(listener: OnTimerTickListener) { // 실시간으로 증폭 가져오기
    private var duration = 0L

    // handler 사용하기
    private val handler = Handler(Looper.getMainLooper())
    private val runnable: Runnable = object: Runnable {

        override fun run() {
            duration += 40L

            // 무한루프 돌리기
            handler.postDelayed(this, 40L)

            listener.onTick(duration)
        }

    }

    fun start() {
        handler.postDelayed(runnable, 40L)
    }

    fun stop() {
        handler.removeCallbacks(runnable)
        duration = 0
    }
}

interface OnTimerTickListener{
    fun onTick(duration: Long)
}