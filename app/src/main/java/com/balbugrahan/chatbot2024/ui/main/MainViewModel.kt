package com.balbugrahan.chatbot2024.ui.main

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.balbugrahan.chatbot2024.base.BaseViewModel
import com.balbugrahan.chatbot2024.data.model.Step
import com.balbugrahan.chatbot2024.data.repository.StepRepository
import com.balbugrahan.chatbot2024.data.repository.WebSocketRepository
import com.balbugrahan.chatbot2024.util.DialogHelper
import com.balbugrahan.chatbot2024.util.JsonHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val webSocketRepository: WebSocketRepository,
    private val stepRepository: StepRepository, // Room kullanımı için
    private val context: Application
) : BaseViewModel(context) {

    private val steps: List<Step> = JsonHelper.loadSteps(context)

    private val _currentStep = MutableLiveData<Step>()
    val currentStep: LiveData<Step> get() = _currentStep

    private val _finishEvent = MutableLiveData<Boolean>()
    val finishEvent: LiveData<Boolean> = _finishEvent


    //Socket bağlantısı viewmodel çağrıldığında başlatılır.
    init {
        webSocketRepository.connectWebSocket()
        observeWebSocketMessages()
        loadInitialStep()
    }

    //Sayfa açılınce ilk adım yüklenir.
    private fun loadInitialStep() {
        _currentStep.value = steps.firstOrNull { it.step == "step_1" }
    }


    //Socketten gelen mesajları observe eder
    private fun observeWebSocketMessages() {
        webSocketRepository.messageLiveData.observeForever { response ->
            val nextStep = steps.find { it.step == response }
            nextStep?.let {
                _currentStep.value = it
                saveStepToRoom(it)
            }
        }
    }
    //Room'a kaydeder.
    private fun saveStepToRoom(step: Step) {
        viewModelScope.launch {
            stepRepository.saveStep(step)
        }
    }
    //Room'dan veri alır.
    private fun getSavedStepsFromRoom() {
        viewModelScope.launch {
            stepRepository.getSavedSteps() // Room'dan veri al
        }
    }
    //Kullanıcı arayüzünde sockete aksiyon gönderir.
    fun sendAction(action: String) {
       webSocketRepository.sendAction(action)
    }
    // ViewModel'den finish tetiklemek için burayı çağrılabiliriz.
    fun onFinishRequested() {
        _finishEvent.postValue(true)
    }

}

