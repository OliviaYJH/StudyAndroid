package com.example.webtoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.webkit.WebViewClient
import android.widget.TextView
import com.example.webtoon.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // viewpager2 설정
        binding.viewPager.adapter = ViewPagerAdapter(this)

        // tablayout 연결
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            run {
                //tab.text = "position $position"

                val textView = TextView(this)
                textView.text = "$position"
                textView.gravity = Gravity.CENTER

                tab.customView = textView // 위에 작성한 뷰가 넘어감
            }
        }.attach()


        /*binding.btn1.setOnClickListener {
            supportFragmentManager.beginTransaction().apply { // 작업 시작
                replace(R.id.fl_container, WebViewFragment()) // WebViewFragment를 항상 새로 생성함
                commit() // 작업 끝남
            }

        }
        binding.btn2.setOnClickListener {
            supportFragmentManager.beginTransaction().apply { // 작업 시작
                replace(R.id.fl_container, BFragment())
                commit() // 작업 끝남
            }

        }*/
    }

    override fun onBackPressed() {
        // super.onBackPressed() 활성화하면 main에서 보여지는 웹툰 화면에서 뒤로가기 버튼을 누르면 아예 앱이 꺼짐
        // 이를 방지하기 위해 back button을 비활성화

        /*
        MainActivity에서 WebViewFragment 안의 함수를 사용하기 위해
         supportFragmentManager 안의 WebViewFragment를 꺼내오기
         */

        val currentFragment = supportFragmentManager.fragments[binding.viewPager.currentItem]
        if (currentFragment is WebViewFragment) {
            if (currentFragment.canGoBack()) {
                currentFragment.goBack()
            } else {
                super.onBackPressed() // 뒤로 갈 페이지가 없으면 앱 종료
            }
        } else {
            super.onBackPressed()
        }
    }
}