package com.example.studypattern

data class ImageResponse(
    val id: String,
    val urls: UrlResponses,
    val color: String
)

data class UrlResponses(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)