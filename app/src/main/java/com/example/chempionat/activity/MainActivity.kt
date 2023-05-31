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
    lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences("Act", Context.MODE_PRIVATE)
        var indAct: Int = pref.getInt("indAct", 1)
        var thread: Thread = object : Thread(){
            override fun run() {
                try {
                    TimeUnit.SECONDS.sleep(2)
                    if(indAct == 0){
                        var e = pref.edit()
                        e.putInt("indAct", 1)
                        e.apply()
                        startActivity(Intent(this@MainActivity, OnBoard::class.java))
                        finish()
                    }
                    if(indAct == 1){
                        startActivity(Intent(this@MainActivity, InputRegist::class.java))
                        finish()
                    }

                    if(indAct == 2){
                        startActivity(Intent(this@MainActivity, CreatePassword::class.java))
                        finish()
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
