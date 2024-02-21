package com.example.news.model

data class NewsModel(
    val title: String,
    val link: String,
    var imageUrl: String? = null
)

// wrapper
fun List<NewsItem>.transform(): List<NewsModel> { // 함수 명은 transform()
    // List<NewsItem>를 확장

    return this.map {
        NewsModel(
            title = it.title ?: "",
            link = it.link ?: "",
            imageUrl = null
        )
    }
}