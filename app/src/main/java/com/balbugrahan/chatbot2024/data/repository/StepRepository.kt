package com.balbugrahan.chatbot2024.data.repository

import com.balbugrahan.chatbot2024.data.model.Step
import com.balbugrahan.chatbot2024.util.StepDao
import com.balbugrahan.chatbot2024.data.model.StepEntity
import javax.inject.Inject

class StepRepository @Inject constructor(
    private val stepDao: StepDao) {
    suspend fun saveStep(step: Step) {
        val entity = StepEntity(
            step = step.step,
            type = step.type,
            contentText = step.content.text,
            imageUrl = if (step.type == "image") step.content.text else null
        )
        stepDao.insertStep(entity)
    }

    suspend fun getSavedSteps(): List<StepEntity> {
        return stepDao.getAllSteps()
    }
}
