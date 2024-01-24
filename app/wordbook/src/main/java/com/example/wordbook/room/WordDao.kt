package com.example.wordbook.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.wordbook.Word

@Dao
interface WordDao { // data access object - 관련 query 사용할 수 있게 함

    // 전체 단어 최신순으로 가져오기 - table 명
    @Query("SELECT * from word ORDER BY id DESC")
    fun getAll(): List<Word>

    @Insert
    fun insert(word: Word)

    @Delete
    fun delete(word: Word)

    @Update
    fun update(word: Word)

    @Query("SELECT * from word ORDER BY id DESC LIMIT 1") // 최신거 하나만 가져오기
    fun getLatestWord(): Word
}