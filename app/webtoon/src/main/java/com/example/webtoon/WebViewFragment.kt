package com.example.webtoon

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.webtoon.databinding.FragmentWebviewBinding

class WebViewFragment(private val position: Int, private val webViewUrl: String) : Fragment() {
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
        binding.webview.webViewClient = WebtoonWebViewClient(binding.progressBar) { url -> // 함수를 넘김
            activity?.getSharedPreferences("WEB_HISTORY", Context.MODE_PRIVATE)?.edit {
                putString("tab$position", url) // 현재 페이지 가져오기
            }
        }
        binding.webview.settings.javaScriptEnabled = true


        binding.webview.loadUrl(webViewUrl) // 이것만 작성하면 웹 브라우저에서 뜨게 됨


        binding.btnBackToLast.setOnClickListener {
            // 부모의 activity를 가져옴
            val sharedPreferences =
                activity?.getSharedPreferences("WEB_HISTORY", Context.MODE_PRIVATE)
            val url = sharedPreferences?.getString("tab$position", "")

            if (url.isNullOrEmpty()) {
                Toast.makeText(context, "마지막 저장 시점이 없습니다", Toast.LENGTH_SHORT).show()
            } else binding.webview.loadUrl(url)
        }
    }

    fun canGoBack(): Boolean {
        // main activity에서 webview에 대한 canGoBack 조회를 할 수 있도록
        return binding.webview.canGoBack()
    }

    fun goBack() {
        binding.webview.goBack()
    }
}