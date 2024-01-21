package com.example.widget

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.widget.databinding.ActivityWidgetBinding

class WidgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWidgetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWidgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToInputView()
        deleteData()
        emergencyCall()
    }

    override fun onResume() {
        super.onResume()

        getDataAndUIUpdate()
    }

    private fun navigateToInputView() {
        binding.goInputActivityButton.setOnClickListener {
            // 명시적 인텐트 - 어디로 연결되는지 명확함
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("intentMessage", "hihi")
            startActivity(intent)
        }
    }

    private fun getDataAndUIUpdate() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)) {
            binding.nameValueTextView.text = getString(NAME, "정현")
            binding.birthdateValueTextView.text = getString(BIRTHDATE,"정현")
            binding.bloodTypeValueTextView.text = getString(BLOOD_TYPE, "정현")
            binding.emergencyContactValueTextView.text = getString(EMERGENCY_CONTACT, "정현")

            val warning = getString(WARNING, "")
            binding.warningTextView.isVisible = warning.isNullOrEmpty().not()
            binding.warningValueTextView.isVisible = warning.isNullOrEmpty().not()
            if (warning.isNullOrEmpty()) {
                binding.warningValueTextView.text = warning
            }

        }
    }

    private fun deleteData() {
        binding.deleteButton.setOnClickListener {
            with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()) {
                clear() // 파일에 있는 모든 정보 삭제
                apply()
            }

            getDataAndUIUpdate()
            Toast.makeText(this, "초기화를 완료했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 전화로 연결
    private fun emergencyCall() {
        binding.emergencyContactLayer.setOnClickListener {
            /*
            전화를 할 수 있는 앱이 여러개
            이 앱에서는 어떻게 전화가 실행될 지 모르기 때문에 - 전화 동작이 가능한 앱을 실행하게 하는 코드
            -> 암시적 인텐트
             */

            with(Intent(Intent.ACTION_VIEW)) {
                val phoneNumber = binding.emergencyContactValueTextView.text.toString()
                    .replace("-", "")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }
}