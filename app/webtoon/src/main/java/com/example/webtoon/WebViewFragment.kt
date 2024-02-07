package com.example.webtoon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.webtoon.databinding.FragmentWebviewBinding

class WebViewFragment: Fragment() {
    private lateinit var binding: FragmentWebviewBinding

    override fun onCreateView( // view가 생성되는 시점
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // view가 만들어진 후 호출
        super.onViewCreated(view, savedInstanceState)

        // 앱 내부에서 띄우기
        binding.webview.webViewClient = WebViewClient()
        binding.webview.settings.javaScriptEnabled = true

        binding.webview.loadUrl("https://google.com") // 이것만 작성하면 웹 브라우저에서 뜨게 됨
    }
}