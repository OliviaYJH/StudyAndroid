package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.databinding.ActivityMainBinding
import com.example.news.model.NewsRss
import com.example.news.model.transform
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://news.google.com/")
        .addConverterFactory(
            TikXmlConverterFactory.create(
                TikXml.Builder()
                    .exceptionOnUnreadXml(false)
                    .build()
            )
        ).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsAdapter = NewsAdapter()
        val newsService = retrofit.create(NewsService::class.java)

        // recyclerview
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        binding.chipFeed.setOnClickListener {
            binding.chipGroup.clearCheck()
            binding.chipFeed.isChecked = true

        }

        binding.chipEntertainment.setOnClickListener {
            // api 호출, 리스트 변경
            binding.chipGroup.clearCheck()
            binding.chipEntertainment.isChecked = true

            newsService.getEntertainmentNews().submitList()
        }

        binding.chipSocial.setOnClickListener {
            binding.chipGroup.clearCheck()
            binding.chipSocial.isChecked = true

            newsService.getSocialNews().submitList()
        }

        binding.chipEconomics.setOnClickListener {
            binding.chipGroup.clearCheck()
            binding.chipEconomics.isChecked = true

            newsService.getEconomicsNews().submitList()
        }

        binding.chipSport.setOnClickListener {
            binding.chipGroup.clearCheck()
            binding.chipSport.isChecked = true

            newsService.getSportsNews().submitList()
        }

        newsService.mainFeed().submitList()

    }

    private fun Call<NewsRss>.submitList() {
        // this는 Call<NewsRss>이고 이 의미는 애가 newsService.mainFeed() 임을 뜻함!

        enqueue(object : Callback<NewsRss> {
            override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
                Log.e("Olivia", "${response.body()?.channel?.items}")

                val list = response.body()?.channel?.items.orEmpty().transform()
                newsAdapter.submitList(list)

                list.forEachIndexed { index, news ->
                    Thread {
                        try {
                            val jsoup =
                                Jsoup.connect(news.link).get() // network 접속은 main thread에서 실행될 수 없음
                            val elements = jsoup.select("meta[property^=og:]")
                            val ogImageNode = elements.find { node ->
                                node.attr("property") == "og:image"
                            }
                            news.imageUrl = ogImageNode?.attr("content")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        runOnUiThread {
                            newsAdapter.notifyItemChanged(index)
                        }
                    }.start()
                }
            }

            override fun onFailure(call: Call<NewsRss>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

}