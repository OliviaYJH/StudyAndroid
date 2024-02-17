package com.example.todaysnotice

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("message")
    val mes: String
)