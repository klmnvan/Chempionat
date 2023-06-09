package com.example.chempionat.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chempionat.Person
import com.example.chempionat.databinding.ActivityMainBinding
import io.paperdb.Paper
import java.util.concurrent.TimeUnit

/**
 * SHARED PREFERENCES - позволяет сохранять простые типы данных на устройстве
 * "Act" - хранилище состояний приложения. ТЕГИ: "indAct" - текущее состояние
 * 0 - первый запуск, пользователь видит OnBoard
 * 1 - пользователь больше не видит OnBoard и сразу переключается на окно регистрации
 * 2 - пользователь зарегистрирован, получен токен, открывается окно создания пароля
 * 3 - пользователь создал пароль, открывается окно ввода пароля
 * "Person" - хранилище данных о пользователе. Здесь хранится:
 * 1. Токен пользователя (тег "token")
 * 2. Пароль пользователя для входа в приложение (тег "password")
 * 3. Почта пользователя (тег "email")
 * PAPER - позволяет сохранять списки объектов любого типа, необходимо подключать доп библиотеку
 * "person" - содержит структуру карты пациента
 */
@Suppress("CAST_NEVER_SUCCEEDS")
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Paper.init(this@MainActivity)
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
    @Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")
    fun init(){
        val prefPerson: SharedPreferences = getSharedPreferences("Person", Context.MODE_PRIVATE)
        Person.token = prefPerson.getString("token", "1").toString()
        Person.password = prefPerson.getString("password", "0000").toString()
        Person.email = prefPerson.getString("email", "example@mail.ru").toString()
        Person.person = Paper.book().read("person", null)
    }
}
