package com.balbugrahan.chatbot2024.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.balbugrahan.chatbot2024.util.StepDao
import com.balbugrahan.chatbot2024.data.model.StepEntity

@Database(entities = [StepEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stepDao(): StepDao
}
