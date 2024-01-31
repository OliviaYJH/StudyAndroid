package com.example.myframe

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myframe.databinding.ActivityFrameBinding
import com.google.android.material.tabs.TabLayoutMediator

class FrameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFrameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar
        binding.toolbar.apply {
            title = "나만의 앨범"
            setSupportActionBar(this)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 활성화

        val images = (intent.getStringArrayExtra("images") ?: emptyArray())
            .map { uriString -> FrameItem(Uri.parse(uriString)) } // FrameItem으로 바꿔주기
            .toList()
        val frameAdapter = FrameAdapter(images)

        binding.vpFrame.adapter = frameAdapter // 어댑터에 연결

        TabLayoutMediator(
            binding.tabFrame,
            binding.vpFrame
        ) { tab, position ->
            binding.vpFrame.currentItem = tab.position
        }.attach()

    }
}