package com.example.repository.model

import com.google.gson.annotations.SerializedName

data class UserDto ( // data transfer object - data를 한번에 주지 않고 변환을 거쳐 줌
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("items")
    val items: List<User>
)