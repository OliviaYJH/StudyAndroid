package com.example.webtoon

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.core.view.isVisible

class WebtoonWebViewClient(private val progressbar: ProgressBar): WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        // url 로딩이 완료됨
        super.onPageFinished(view, url)

        progressbar.visibility = View.GONE
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        // 페이지 로딩이 시작했을 때
        super.onPageStarted(view, url, favicon)

        progressbar.visibility = View.VISIBLE
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        // request에 대해서 receiver가 error 발생한 경우
        super.onReceivedError(view, request, error)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        // 페이지 로딩 여부 결정함수 -> true이면 안띄움, false는 띄움
        return false
    }
}