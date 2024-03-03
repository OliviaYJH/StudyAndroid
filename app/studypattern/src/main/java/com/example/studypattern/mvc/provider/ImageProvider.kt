package com.example.studypattern.mvc.provider

import android.os.Build.VERSION_CODES.P
import com.example.studypattern.ImageResponse
import com.example.studypattern.RetrofitManager
import retrofit2.Call
import retrofit2.Response

class ImageProvider(private val callback: Callback) {

    fun getRandomImage() {
        RetrofitManager.imageService.getRandomImages()
            .enqueue(object: retrofit2.Callback<ImageResponse> {
                override fun onResponse(
                    call: Call<ImageResponse>,
                    response: Response<ImageResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback.loadImage(it.urls.regular, it.color)
                        }
                    }
                }

                override fun onFailure(call: Call<ImageResponse>, t: Throwable) {

                }

            })
    }
    interface Callback {
        fun loadImage(url: String, color: String)
    }
}