package com.example.wordbook

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
// data를 holding 하는 class
@Entity(tableName = "word") // table로 사용하기 위해
data class Word(
    val word: String,
    val mean: String,
    val type: String,

    // key 값 지정하기(자동 생성되게)
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable
