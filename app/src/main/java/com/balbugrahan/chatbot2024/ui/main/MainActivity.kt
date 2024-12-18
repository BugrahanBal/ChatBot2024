package com.balbugrahan.chatbot2024.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.balbugrahan.chatbot2024.databinding.ActivityMainBinding
import com.balbugrahan.chatbot2024.ui.StepAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter = StepAdapter { action -> viewModel.sendAction(action) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.currentStep.observe(this) { step ->
            adapter.addStep(step) // Yeni step ekle
            scrollToBottom()      // RecyclerView'ı en aşağıya kaydır.
        }
    }

    private fun scrollToBottom() {
        binding.recyclerView.post {
            binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
        }
    }

    override fun onBackPressed() {
        //tek activity oldugundan gerek yok ileride canlı sohbet akışı söz konusu olursa burası önemli olacaktır.
    }

    //uygulama açma kapamada cacheden getsaveden data alabiliriz.
    //Fragmenta cevirebiliriz
    //Görünümler iyileştirelecek
    //Hata durumlarını ele alabiliriz.
    //verilen json dosyasında ufak bir düzeltme yapıldı
    //sohbet bitimi vs icin handle action kullanıp websocket disconnet yapılabilir.
    //Read me düzenlenebilir
    //fonttan yazılar yazılabilir
    //freepik icon kullanıldı
    //örnek bir resim url i olusturuldu
}
