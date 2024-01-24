package com.example.wordbook

import androidx.room.Entity
import androidx.room.PrimaryKey

// data를 holding 하는 class
@Entity(tableName = "word") // table로 사용하기 위해
data class Word(
    val word: String,
    val mean: String,
    val type: String,

    // key 값 지정하기(자동 생성되게)
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
