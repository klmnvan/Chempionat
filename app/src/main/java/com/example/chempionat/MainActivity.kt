package com.example.chempionat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chempionat.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val MY_SETTINGS = "my_settings"
    object goActicity{
        var bool: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sp = getSharedPreferences(
            MY_SETTINGS,
            Context.MODE_PRIVATE
        )
        val hasVisited = sp.getBoolean("hasVisited", false)
        var thread: Thread = object : Thread(){
            override fun run() {
                try {
                    TimeUnit.SECONDS.sleep(2)
                    //Сработает только в сессии2
                    if(!hasVisited){
                        val e: SharedPreferences.Editor = sp.edit()
                        e.putBoolean("hasVisited", true)
                        e.commit() // не забудьте подтвердить изменения
                        startActivity(Intent(this@MainActivity, OnBoard::class.java))
                    }
                    else{
                        startActivity(Intent(this@MainActivity, InputRegist::class.java))
                    }

                }
                catch (e: InterruptedException){
                    e.printStackTrace()
                }
            }
        }
        thread.start()
    }
}
