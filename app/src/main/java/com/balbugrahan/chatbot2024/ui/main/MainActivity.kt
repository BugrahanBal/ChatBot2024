package com.balbugrahan.chatbot2024.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.balbugrahan.chatbot2024.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment())
                .commit()
        }
    }
}


    //uygulama açma kapamada cacheden getsaveden data alabiliriz.
    //Fragmenta cevirebiliriz
    //Hata durumlarını ele alabiliriz.

    //sohbet bitimi vs icin handle action kullanıp websocket disconnet yapılabilir.
    //Read me düzenlenebilir
    //fonttan yazılar yazılabilir

    //verilen json dosyasında ufak bir düzeltme yapıldı content içeriği sadece text olanların header eksikliği giderildi.
    //verilen json dosyasına örnek bir resim urli eklendi
    //Uygulama iconu için freepik sitesinden icon seçildi.

