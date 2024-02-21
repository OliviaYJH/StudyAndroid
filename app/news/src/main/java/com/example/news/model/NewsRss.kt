package com.example.news.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rss")
data class NewsRss(
    @Element(name = "channel")
    val channel: RssChannel
)

@Xml(name = "channel")
data class RssChannel(
    @PropertyElement(name = "title") // propertyelement: 더 이상 하위 요소가 없음
    val title: String,
    @Element(name = "item") // element는 하위 요소 NewsEntity 존재
    val items: List<NewsItem>? = null
)

@Xml(name = "item")
data class NewsItem(
    @PropertyElement(name = "title")
    val title: String? = null,
    @PropertyElement(name = "link")
    val link: String? = null
)
