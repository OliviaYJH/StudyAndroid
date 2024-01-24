package com.example.wordbook.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wordbook.Word

@Database(entities = [Word::class], version = 1) // 어떤 entity를 관리하는지 추가하기
abstract class WordDatabase : RoomDatabase() {
    // dao 가져오기
    abstract fun wordDao(): WordDao

    companion object {
        private var INSTANCE: WordDatabase? = null

        // 초기화 및 instance 꺼내는 함수
        fun getInstance(context: Context): WordDatabase? { // singleton으로 사용하겠다
            if (INSTANCE == null) {

                // 하나만 생성되기 위하여 synchronized로 감싸기
                synchronized(WordDatabase::class.java) {

                    // 초기화 작업 필요
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WordDatabase::class.java,
                        "word-database.db"
                    ).build()
                }


            }
            return INSTANCE
        }
    }
}