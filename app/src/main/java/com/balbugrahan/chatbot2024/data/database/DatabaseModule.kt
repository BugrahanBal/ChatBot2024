package com.balbugrahan.chatbot2024.data.database

import android.content.Context
import androidx.room.Room
import com.balbugrahan.chatbot2024.base.AppDatabase
import com.balbugrahan.chatbot2024.util.StepDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
    @Provides
    fun provideStepDao(database: AppDatabase): StepDao {
        return database.stepDao()
    }
}
