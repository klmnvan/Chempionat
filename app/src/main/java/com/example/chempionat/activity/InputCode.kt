package com.example.chempionat.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chempionat.Person
import com.example.chempionat.api.ApiRequest
import com.example.chempionat.databinding.ActivityInputCodeBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


class InputCode : AppCompatActivity() {
    lateinit var binding: ActivityInputCodeBinding
    var code: String = "0000"
    lateinit var email: String
    var bool: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var arguments = intent.extras
        email = arguments?.getString("email").toString()
        init()
        timer()
    }

    /**
     * timer() - отдельный метод, в котором запускается таймер
     */
    fun timer(){
        val timer = object : CountDownTimer(600000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                var sec = millisUntilFinished / 10000
                binding.textTimer.text = "Отправить код повторно можно будет через ${sec} секунд"
            }
            override fun onFinish() {
            }
        }
        timer.start()
    }

    fun init()
    {
        with(binding){
            num1.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if(binding.num1.text.isNotEmpty()){
                        binding.num2.requestFocus()
                    }
                }
            })
            num2.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if(binding.num2.text.isNotEmpty()){
                        binding.num3.requestFocus()
                    }
                }
            })
            num3.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if(binding.num3.text.isNotEmpty()){
                        binding.num4.requestFocus()
                        binding.num4.text = null
                    }
                }
            })
            num4.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if(binding.num4.text.isNotEmpty()){
                        verification()
                    }
                }
            })
            buttonBack.setOnClickListener(){
                startActivity(Intent(this@InputCode, InputRegist::class.java))
                finish()
            }
        }

    }

    fun verification(){
        with(binding){
            if(num1.text.isNotEmpty() && num2.text.isNotEmpty() && num3.text.isNotEmpty() && num4.text.isNotEmpty()){
                code = num1.text.toString() + num2.text.toString() + num3.text.toString() + num4.text.toString()
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val httpClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
                val gson = GsonBuilder() //Нужно, чтобы получилось принять строку с сервера и убрать ошибку в консоли
                    .setLenient()
                    .create()
                val api = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl("http://iis.ngknn.ru/NGKNN/%D0%9C%D0%B0%D0%BC%D1%88%D0%B5%D0%B2%D0%B0%D0%AE%D0%A1/MedicMadlab/")
                    .client(httpClient)
                    .build()
                val requestApi = api.create(ApiRequest::class.java)
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = requestApi.postCode(email, code).awaitResponse()
                        if(response.isSuccessful){
                            val data = response.body()!!
                            Log.d(TAG, "ТОКИН: " + data)
                            Person.token = data
                            runOnUiThread { token() }
                        /*
                            runOnUiThread { Person.token = data }*/
                        }
                        bool = false
                        /*runOnUiThread { Toast.makeText(this@InputCode, "Код неверный",
                            Toast.LENGTH_SHORT).show() }*/
                    }
                    catch (e: Exception){
                        bool = false
                        Log.d(TAG, e.toString())
                        /*var prefPerson: SharedPreferences = getSharedPreferences("Person", Context.MODE_PRIVATE)
                        var eP = prefPerson.edit()
                        eP.putString("email", email)
                        eP.apply()
                        var prefAct: SharedPreferences = getSharedPreferences("Act", Context.MODE_PRIVATE)
                        var eA = prefAct.edit()
                        eA.putInt("indAct", 2)
                        eA.apply()*/
                        /*startActivity(Intent(this@InputCode, CreatePassword::class.java))
                        finish()*/
                    }
                }
            }
        }
    }

    fun token(){
        Log.d(TAG, "Я зашёл в token()")
        if(Person.token.isNotEmpty()){
            Toast.makeText(this@InputCode, "Токин пришёл", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "ТОКИН ПРИШЁЛ И ВСЁ ШИКАРНО")
            startActivity(Intent(this@InputCode, CreatePassword::class.java))
        }
        else {
            Toast.makeText(this@InputCode, "Неверный код", Toast.LENGTH_SHORT).show()
        }
        if(bool){
            with(binding){
                num1.text = null
                num2.text = null
                num3.text = null
                num4.text = null
                num1.requestFocus()
            }
        }
    }
}