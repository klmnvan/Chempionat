package com.example.chempionat.activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.chempionat.Person
import com.example.chempionat.R
import com.example.chempionat.api.ApiRequest
import com.example.chempionat.databinding.ActivityCreateMapBinding
import com.example.chempionat.models.AddressModel
import com.example.chempionat.models.PersonModel
import io.paperdb.Paper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CreateMap : AppCompatActivity(), FragmentAddress.Listener {
    lateinit var binding: ActivityCreateMapBinding
    var index: Int = 0
    lateinit var gender: String
    var fragmentAddress = FragmentAddress(this@CreateMap)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Paper.init(this@CreateMap)
        binding.spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val tv = view as TextView
                index = position
                tv.setTextSize(14F)
                if (position == 0) {
                    tv.setTextColor(Color.GRAY)
                } else {
                    tv.setTextColor(Color.BLACK)
                }
                if(index != 0)
                {
                    binding.spinnerGender.background = getDrawable(R.drawable.map_input_style)
                }
                if(index == 1 ){
                    gender = "м"
                }
                if(index == 2)
                {
                    gender = "ж"
                }
                textChecked()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        init()
    }
    fun init(){
        binding.buttonCreate.setOnClickListener(){
            startActivity(Intent(this@CreateMap, Home::class.java))
        }
        binding.adress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun afterTextChanged(s: Editable?) {
                binding.adress.background = getDrawable(R.drawable.map_input_style)
                textChecked()
            }
        })
        binding.inputTextPatronymic.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun afterTextChanged(s: Editable?) {
                binding.inputTextPatronymic.background = getDrawable(R.drawable.map_input_style)
                textChecked()
            }
        })
        binding.inputTextSurname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun afterTextChanged(s: Editable?) {
                binding.inputTextSurname.background = getDrawable(R.drawable.map_input_style)
                textChecked()
            }
        })
        binding.inputTextBirthday.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun afterTextChanged(s: Editable?) {
                binding.inputTextBirthday.background = getDrawable(R.drawable.map_input_style)
                textChecked()
            }
        })
        binding.buttonPropusk.setOnClickListener(){
            startActivity(Intent(this@CreateMap, Home::class.java))
            finish()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun textChecked()
    {
        if(binding.adress.text.isNotEmpty() && binding.inputTextPatronymic.text.isNotEmpty()
            && binding.inputTextSurname.text.isNotEmpty() && binding.inputTextBirthday.text.isNotEmpty() && index != 0){
            binding.buttonCreate.background = getDrawable(R.drawable.shape_button2)
            binding.buttonCreate.setOnClickListener{
                Person.person = PersonModel(0,binding.inputTextSurname.text.toString(),binding.adress.text.toString(),binding.inputTextPatronymic.text.toString(),
                    binding.inputTextBirthday.text.toString(), gender,"1")
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
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
                        val bearer = "Bearer ${Person.token}"
                        requestApi.postProfile(bearer)
                        Log.d(TAG, "Success")
                    }
                    catch (e: Exception){
                        Log.d(TAG, e.toString())
                    }
                }


                Paper.book().write("person", Person.person!!)
                startActivity(Intent(this@CreateMap, Home::class.java))
            }
        }
    }

    override fun setText(addressModel: AddressModel) {
        val thisAddress = addressModel.address + " kv. " + addressModel.kvartira
        binding.adress.setText(thisAddress)
        fragmentAddress.dismiss()
    }


}