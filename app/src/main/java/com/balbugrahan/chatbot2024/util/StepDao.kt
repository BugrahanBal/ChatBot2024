package com.balbugrahan.chatbot2024.util

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.balbugrahan.chatbot2024.data.model.StepEntity

@Dao
interface StepDao {

    //Room işlemleri burada yapılır.
    @Insert
    suspend fun insertStep(step: StepEntity)

    @Query("SELECT * FROM steps ORDER BY id ASC")
    suspend fun getAllSteps(): List<StepEntity>
}
