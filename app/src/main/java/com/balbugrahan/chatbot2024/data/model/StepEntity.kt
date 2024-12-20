package com.balbugrahan.chatbot2024.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps")
data class StepEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val step: String,
    val type: String,
    val contentText: String,
    val imageUrl: String? = null)
