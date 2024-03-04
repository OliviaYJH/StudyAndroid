package com.example.studypattern.mvp.repository

import com.example.studypattern.ImageResponse
import com.example.studypattern.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageRepositoryImpl: ImageRepository {
    override fun getRandomImage(callBack: ImageRepository.CallBack) {
        RetrofitManager.imageService.getRandomImages()
            .enqueue(object: Callback<ImageResponse> {
                override fun onResponse(
                    call: Call<ImageResponse>,
                    response: Response<ImageResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callBack.loadImage(it.urls.regular, it.color)
                        }
                    }
                }

                override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
}