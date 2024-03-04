package com.example.studypattern.mvp.repository

interface ImageRepository {
    fun getRandomImage(callBack: CallBack)

    interface CallBack {
        fun loadImage(url: String, color: String)
    }
}