package com.example.chempionat.activity

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chempionat.R
import com.example.chempionat.api.ApiRequest
import com.example.chempionat.databinding.ActivityInputRegistBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class InputRegist : AppCompatActivity() {
    lateinit var binding: ActivityInputRegistBinding
    lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputRegistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        textChanged()
    }

    /**
     * textChanged() - отдельный метод, в который кладём событие отслеживания состояния
     * поля ввода для email
     */
    fun textChanged(){
        with(binding){
            inputEmailtext.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if(inputEmailtext.text.isNotEmpty()){
                        buttonDalee.background = this@InputRegist.getDrawable(R.drawable.shape_button2)
                    }
                    else{
                        buttonDalee.background = this@InputRegist.getDrawable(R.drawable.shape_button)
                    }
                }
            })
        }
    }

    /**
     * init() - отдельный метод, в который кладём событие нажатия на кнопку, внутри которого
     * отправляем запрос на сервер, если символов в поле больше 3.
     */

    fun init(){
        with(binding){
            buttonDalee.setOnClickListener(){
                email = inputEmailtext.text.toString()
                if(inputEmailtext.text.length > 3){
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level
                    val httpClient = OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .build()
                    val api = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://iis.ngknn.ru/NGKNN/%D0%9C%D0%B0%D0%BC%D1%88%D0%B5%D0%B2%D0%B0%D0%AE%D0%A1/MedicMadlab/")
                        .client(httpClient)
                        .build()
                    val requestApi = api.create(ApiRequest::class.java)
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            requestApi.postEmail(email).awaitResponse()
                            Log.d("Response","success")
                        }
                        catch (e: Exception){
                            Log.d(TAG, e.toString())
                        }
                    }
                    var pref: SharedPreferences = getSharedPreferences("Person", Context.MODE_PRIVATE)
                    var e = pref.edit()
                    e.putString("email", email)
                    e.apply()
                    //intent.putExtra("email", email)
                    startActivity(Intent(this@InputRegist, InputCode::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this@InputRegist, "Вы ввели слишком мало символов", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}