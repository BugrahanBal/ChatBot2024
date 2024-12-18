package com.balbugrahan.chatbot2024.data.model

data class Step(
    val step: String,
    val type: String,
    val content: Content,
    val action: String
)
data class Content(
    val text: String,
    val buttons: List<ButtonAction>? = null
)
data class ButtonAction(
    val label: String,
    val action: String
)

