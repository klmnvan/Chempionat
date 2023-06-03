package com.example.chempionat.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chempionat.Person
import com.example.chempionat.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

/**
"Act" - хранилище состояний приложения. "indAct" - текущее состояние
0 - первый запуск, пользователь видит OnBoard
1 - пользователь больше не видит OnBoard и сразу переключается на окно регистрации
2 - пользователь зарегистрирован, получен токен, открывается окно создания пароля
3 - пользователь создал пароль, открывается окно ввода пароля
 */
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences("Act", Context.MODE_PRIVATE)
        var indAct: Int = pref.getInt("indAct", 0).toInt()
        indAct = 3
        val thread: Thread = object : Thread(){
            override fun run() {
                try {
                    TimeUnit.SECONDS.sleep(2)
                    if(indAct == 0){
                        val e = pref.edit()
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
                    if(indAct == 3){
                        startActivity(Intent(this@MainActivity, InputPassword::class.java))
                        finish()
                    }
                }
                catch (e: InterruptedException){
                    e.printStackTrace()
                }
            }
        }
        thread.start()
        init()
    }

    /**
     * init() - метод, в котором будет проходить инициализация данных в структуру Person из хранилища
     * SharedPreferences
     */
    fun init(){
        val prefPerson: SharedPreferences = getSharedPreferences("Person", Context.MODE_PRIVATE)
        Person.token = prefPerson.getString("token", "1").toString()
        Person.password = prefPerson.getString("password", "0000").toString()
    }
}
