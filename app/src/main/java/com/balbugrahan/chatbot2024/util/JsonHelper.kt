package com.balbugrahan.chatbot2024.util

import android.content.Context
import com.balbugrahan.chatbot2024.data.model.Step
import com.google.gson.Gson
import java.io.InputStream

object JsonHelper {
    //Assest dosyasına koyduğumuz veri akışı rule olarak buradan okuyoruz
    fun loadSteps(context: Context): List<Step> {
        val inputStream: InputStream = context.assets.open("flow.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        return Gson().fromJson(json, Array<Step>::class.java).toList()
    }
}