package com.example.stopwatch

import android.media.AudioManager
import android.media.ToneGenerator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.example.stopwatch.databinding.ActivityStopWatchBinding
import com.example.stopwatch.databinding.DialogCountdownSettingBinding
import java.util.Timer
import kotlin.concurrent.timer

class StopWatchActivity : AppCompatActivity() {
    private var _binding: ActivityStopWatchBinding? = null
    private val binding get() = _binding!!

    private var countDownSecond = 10
    private var currentCountdownDeciSecond = countDownSecond * 10

    private var currentDeciSecond = 0
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityStopWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvCountDown.setOnClickListener {
            showCountDownSettingDialog()
        }

        binding.btnStart.setOnClickListener {
            start()

            binding.btnStart.isVisible = false
            binding.btnStop.isVisible = false
            binding.btnPause.isVisible = true
            binding.btnLap.isVisible = true
        }

        binding.btnStop.setOnClickListener {
            showAlertDialog()
        }

        binding.btnPause.setOnClickListener {
            pause()

            binding.btnStart.isVisible = true
            binding.btnStop.isVisible = true
            binding.btnPause.isVisible = false
            binding.btnLap.isVisible = false
        }

        binding.btnLap.setOnClickListener {
            lap()

            binding.btnStart.isVisible = false
            binding.btnStop.isVisible = false
            binding.btnPause.isVisible = true
            binding.btnLap.isVisible = true
        }

        initCountdownViews()
    }

    private fun initCountdownViews() {
        binding.tvCountDown.text = String.format("%02d", countDownSecond)
        binding.pbCountDown.progress = 100 // 맨 처음은 100%
    }

    private fun start() {
        // 1초 = 1000ms -> 이 앱에서는 0.1초마다 check == 100ms
        // timer => Worker Thread가 생긴 것임
        timer = timer(initialDelay = 0, period = 100) {
            if (currentCountdownDeciSecond == 0) {
                // count up
                currentDeciSecond += 1 // 0.1초씩 증가
                Log.d("currentDeciSecond", currentDeciSecond.toString())

                val minutes = currentDeciSecond.div(10) / 60
                val second = currentDeciSecond.div(10) % 60
                val deciSeconds = currentDeciSecond % 10

                // runOnUiThread 방법
                runOnUiThread {// Activity 안에 있는 메서드
                    // UI를 수정하는 작업
                    binding.tvTime.text = String.format("%02d:%02d", minutes, second)
                    binding.tvTick.text = deciSeconds.toString()

                    // countdown 관련 UI 요소 안보이게
                    binding.groupCountdown.isVisible = false
                }
            } else {
                // count down
                currentCountdownDeciSecond -= 1
                val seconds = currentCountdownDeciSecond / 10
                val progress =
                    currentCountdownDeciSecond / (countDownSecond * 10f) * 100 // 백분율 float 형

                // View에서 post하는 방법
                binding.root.post {
                    // UI를 수정하는 작업
                    binding.tvCountDown.text = String.format("%02d", seconds)
                    binding.pbCountDown.progress = progress.toInt()
                }

            }

            if (currentDeciSecond == 0 && currentCountdownDeciSecond < 31 && currentCountdownDeciSecond % 10 == 0) { // 3초 전부터 딱 떨어지는 시점에 실행됨
                // beep sound on
                Log.d("ToneType", "Success")
                val toneType = if (currentCountdownDeciSecond == 0) ToneGenerator.TONE_CDMA_HIGH_L else ToneGenerator.TONE_CDMA_ANSWER
                ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME)
                    .startTone(toneType, 100) // 0.1초 동안 지속됨

            }
        }
    }

    private fun stop() {
        binding.btnStart.isVisible = true
        binding.btnStop.isVisible = true
        binding.btnPause.isVisible = false
        binding.btnLap.isVisible = false

        // 초기화
        currentDeciSecond = 0 // data 변경
        // UI 변경
        binding.tvTime.text = "00:00"
        binding.tvTick.text = "0"

        binding.groupCountdown.isVisible = true
        initCountdownViews()

        binding.linearLapContainer.removeAllViews()
    }

    private fun pause() {
        timer?.cancel()
        timer = null
    }

    private fun lap() {
        // 시작 안된 상태
        if (currentDeciSecond == 0) return

        val container = binding.linearLapContainer
        TextView(this).apply {
            textSize = 20f
            gravity = Gravity.CENTER

            val minutes = currentDeciSecond.div(10) / 60
            val seconds = currentDeciSecond.div(10) % 60
            val deciSeconds = currentDeciSecond % 10
            text = "${container.childCount.inc()}. " + String.format(
                "%02d:%02d %01d",
                minutes,
                seconds,
                deciSeconds
            )
            // 1. 01:03 0
            setPadding(30)
        }.let { laptv ->
            container.addView(laptv, 0) // 새로 추가될 때마다 맨 위로 오게
        }
    }

    private fun showCountDownSettingDialog() {
        AlertDialog.Builder(this).apply {
            // dialog 띄우기
            val settingBinding = DialogCountdownSettingBinding.inflate(layoutInflater)
            // 기본 설정
            with(settingBinding.npCountdownSecond) {
                maxValue = 20
                minValue = 0
                value = countDownSecond // default 값
            }
            setTitle("카운트다운 설정")
            setView(settingBinding.root)

            // 나머지 설정
            setPositiveButton("확인") { dialog, id ->
                countDownSecond = settingBinding.npCountdownSecond.value
                currentCountdownDeciSecond = countDownSecond * 10

                binding.tvCountDown.text = String.format("%02d", countDownSecond)
            }
            setNegativeButton("취소", null)
        }.show()
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this).apply {
            //setTitle()
            setMessage("종료하시겠습니까?")
            setPositiveButton("네") { dialog, id ->
                stop()
            }
            setNegativeButton("아니오", null)
        }.show()
    }

}