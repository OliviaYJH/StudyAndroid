package com.example.recorder

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.recorder.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity(), OnTimerTickListener {
    private lateinit var binding: ActivityMainBinding
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var fileName: String = ""
    private var state: State = State.RELEASE

    private lateinit var timer: Timer

    companion object {
        const val REQUEST_RECORD_AUDIO_CODE = 200
    }

    // 릴리즈 -> 녹음증 -> 릴리즈
    // 릴리즈 -> 재생 -> 릴리즈
    private enum class State {
        RELEASE, RECORDING, PLAYING
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fileName = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"
        timer = Timer(this)

        // 권한 요청
        binding.btnRecord.setOnClickListener {
            when (state) {
                State.RELEASE -> {
                    // 녹음 상태로 이동
                    record()
                }

                State.PLAYING -> {

                }

                State.RECORDING -> {
                    // stop으로 이동
                    onRecord(false)
                }
            }

        }

        binding.btnPlay.setOnClickListener {
            when (state) {
                State.RELEASE -> {
                    // 녹음 상태로 이동
                    onPlay(true)
                }

                else -> {
                    // do nothing
                }
            }
        }
        binding.btnPlay.isEnabled = false
        binding.btnPlay.alpha = 0.3f

        binding.btnStop.setOnClickListener {
            when (state) {
                State.RECORDING -> {
                    onPlay(false)
                }

                else -> {
                    // do nothing
                }
            }
        }
    }

    private fun record() {
        // 권한을 얻어오는 부분
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {
                // 권한이 허용됨 -> 실제 녹음을 시작하면 됨
                onRecord(true)
            }

            // 교육용 팝업 띄우기
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.RECORD_AUDIO
            ) -> {
                showPermissionRationalDialog()
            }

            else -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.RECORD_AUDIO),
                    REQUEST_RECORD_AUDIO_CODE
                )
            }
        }
    }

    // 녹음
    private fun onRecord(start: Boolean) = if (start) {
        startRecording()
    } else {
        stopRecording()
    }

    private fun onPlay(start: Boolean) = if (start) {
        startPlaying()
    } else {
        stopPlaying()
    }

    private fun startRecording() {
        state = State.RECORDING

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC) // 마이크를 사용하겠다
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) // 어떤 형식?
            setOutputFile(fileName) // 어디에 작성할 것인가
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare() // 준비 단계
            } catch (e: IOException) {
                Log.e("ARP", "prepare() failed $e")
            }

            start() // 시작
        }

        binding.waveFormView.clearData()
        timer.start()

        // 버튼 변경
        binding.btnRecord.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.ic_stop)
        )
        binding.btnRecord.imageTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))

        binding.btnPlay.isEnabled = false
        binding.btnPlay.alpha = 0.3f // 조금 투명해짐
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        timer.stop()
        state = State.RELEASE

        binding.btnRecord.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.ic_record)
        )
        binding.btnRecord.imageTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
        binding.btnPlay.isEnabled = true
        binding.btnPlay.alpha = 1.0f
    }

    private fun startPlaying() {
        state = State.PLAYING

        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
            } catch (e: IOException) {
                Log.e("APP", "media player prepare fail $e")
            }

            start()
        }

        binding.waveFormView.clearWave()
        timer.start()

        player?.setOnCompletionListener {
            // 파일 재생이 끝났을 때 실행됨
            stopPlaying()
        }

        // record 버튼 비활성화
        binding.btnRecord.isEnabled = false
        binding.btnRecord.alpha = 0.3f
    }

    private fun stopPlaying() {
        state = State.RELEASE

        player?.release()
        player = null

        timer.stop()

        binding.btnRecord.isEnabled = true
        binding.btnRecord.alpha = 1.0f
    }

    private fun showPermissionRationalDialog() {
        AlertDialog.Builder(this)
            .setMessage("녹음 권한을 켜주셔야 앱을 정상적으로 사용할 수 있습니다.")
            .setPositiveButton("권한 허용하기") { _, _ ->
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.RECORD_AUDIO),
                    REQUEST_RECORD_AUDIO_CODE
                )
            }
            .setNegativeButton("취소") { dialogInterface, _ ->
                dialogInterface.cancel()
            }.show()
    }

    private fun showPermissionSettingDialog() {
        AlertDialog.Builder(this)
            .setMessage("녹음 권한을 켜주셔야 앱을 정상적으로 사용할 수 있습니다. 앱 설정 화면으로 진입하셔서 권한을 켜주세요.")
            .setPositiveButton("권한 변경하러 가기") { _, _ ->
                navigateToAppSetting()
            }
            .setNegativeButton("취소") { dialogInterface, _ ->
                dialogInterface.cancel()
            }.show()
    }

    private fun navigateToAppSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        } // Detail Setting으로 이동
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val audioRecordPermissionGranted = requestCode == REQUEST_RECORD_AUDIO_CODE
                && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        if (audioRecordPermissionGranted) {
            onRecord(true)

        } else {
            // 권한 팝업이 필요한지 체크

            // 교육용 팝업 띄우기
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.RECORD_AUDIO
                )
            ) {
                showPermissionRationalDialog()
            } else {
                // 교육용 팝업이 필요 없는 경우(이미 봤는데 허용하지 않은 경우)

                showPermissionSettingDialog()
            }
        }
    }

    override fun onTick(duration: Long) {
        val millisecond = duration % 1000
        val second = (duration / 1000) % 60
        val minute = (duration / 1000 / 60)

        binding.tvTimer.text = String.format("%02d:%02d.%02d", minute, second, millisecond / 10)

        if (state == State.PLAYING) {
            binding.waveFormView.replayAmplitude()
        } else if (state == State.RECORDING) {
            binding.waveFormView.addAmplitude(recorder?.maxAmplitude?.toFloat() ?: 0f)
        }
    }
}