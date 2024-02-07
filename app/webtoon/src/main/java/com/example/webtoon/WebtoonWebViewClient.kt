package com.example.webtoon

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

class WebtoonWebViewClient(
    private val progressbar: ProgressBar,
    private val saveData: (String) -> Unit // String 값을 받고 return형이 Unit인 함수를 받겠다
): WebViewClient() {

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

        if (request != null && request.url.toString().contains("comic.naver.com/webtoon/detail")) {
            saveData(request.url.toString())
        }

        return !(request != null && request.url.toString().contains("comic.naver.com/"))
        // 이렇게 작성하면 네이버 웹툰 외의 페이지로는 이동이 불가능해짐
    }
}