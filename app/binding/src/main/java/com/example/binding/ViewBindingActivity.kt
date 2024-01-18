package com.example.binding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.binding.databinding.ActivityViewBindingBinding

class ViewBindingActivity : AppCompatActivity() {
    private var _binding: ActivityViewBindingBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityViewBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvViewBinding.text = "First"
    }


    // 화면 전환 시 데이터 저장
    override fun onSaveInstanceState(outState: Bundle) {
        binding.tvViewBinding.text = "Vertical"
        outState.putString("textMessage", binding.tvViewBinding.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.run {
            binding.tvViewBinding.text = getString("textMessage")
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

}