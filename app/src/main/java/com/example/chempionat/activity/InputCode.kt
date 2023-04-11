package com.example.chempionat.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.chempionat.R
import com.example.chempionat.api.ApiRequest
import com.example.chempionat.databinding.ActivityInputCodeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

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
                var sec = millisUntilFinished / 1000
                binding.textTimer.text = "Отправить код повторно можно будет через ${sec} секунд"
            }
            override fun onFinish() {
            }
        }
        timer.start()
    }

    fun init()
    {
        binding.num1.addTextChangedListener(object: TextWatcher{
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
        binding.num2.addTextChangedListener(object: TextWatcher{
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
        binding.num3.addTextChangedListener(object: TextWatcher{
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
        binding.num4.addTextChangedListener(object: TextWatcher{
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
    }

    fun verification(){
        with(binding){
            if(num1.text.isNotEmpty() && num2.text.isNotEmpty() && num3.text.isNotEmpty() && num4.text.isNotEmpty()){
                code = num1.text.toString() + num2.text.toString() + num3.text.toString() + num4.text.toString()
                val interceptor = HttpLoggingInterceptor()
                interceptor.level

                val httpClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
                val api = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://medic.madskill.ru")
                    .client(httpClient)
                    .build()
                val requestApi = api.create(ApiRequest::class.java)
                CoroutineScope(Dispatchers.IO).launch{
                    try {
                        requestApi.postCode(code, email).awaitResponse()
                        runOnUiThread { Toast.makeText(this@InputCode, "Код неверный",
                            Toast.LENGTH_SHORT).show() }
                    }
                    catch (e: Exception){
                        bool = false
                        Log.d(ContentValues.TAG, e.toString())
                        var prefPerson: SharedPreferences = getSharedPreferences("Person", Context.MODE_PRIVATE)
                        var eP = prefPerson.edit()
                        eP.putString("email", email)
                        eP.apply()
                        var prefAct: SharedPreferences = getSharedPreferences("Act", Context.MODE_PRIVATE)
                        var eA = prefAct.edit()
                        eA.putInt("indAct", 2)
                        eA.apply()
                        startActivity(Intent(this@InputCode, CreatePassword::class.java))
                        finish()
                    }
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
    }
}