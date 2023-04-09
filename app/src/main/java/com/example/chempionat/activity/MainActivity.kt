package com.example.chempionat.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chempionat.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var pref: SharedPreferences? = null
    object goActicity{
        var bool: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences("Act", Context.MODE_PRIVATE)
        val indexAct = pref?.getInt("IndexAct", 0)
        var thread: Thread = object : Thread(){
            override fun run() {
                try {
                    TimeUnit.SECONDS.sleep(2)
                    //Сработает только в сессии2
                    if(indexAct == 0){
                        val e = pref?.edit()
                        e?.putInt("IndexAct", 1)
                        e?.apply() // не забудьте подтвердить изменения
                        startActivity(Intent(this@MainActivity, OnBoard::class.java))
                    }
                   if(indexAct == 1){
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
