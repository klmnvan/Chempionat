package com.example.chempionat.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import com.example.chempionat.Person
import com.example.chempionat.R
import com.example.chempionat.databinding.ActivityCreatePasswordBinding
import com.example.chempionat.databinding.ActivityInputPasswordBinding

class InputPassword: AppCompatActivity() {
    lateinit var binding: ActivityInputPasswordBinding
    var password: String = ""
    var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        with(binding){
            buttonCircle0.setOnClickListener(){
                sumPassword(buttonCircle0.text.toString())
            }
            buttonCircle1.setOnClickListener(){
                sumPassword(buttonCircle1.text.toString())
            }
            buttonCircle2.setOnClickListener(){
                sumPassword(buttonCircle2.text.toString())
            }
            buttonCircle3.setOnClickListener(){
                sumPassword(buttonCircle3.text.toString())
            }
            buttonCircle4.setOnClickListener(){
                sumPassword(buttonCircle4.text.toString())
            }
            buttonCircle5.setOnClickListener(){
                sumPassword(buttonCircle5.text.toString())
            }
            buttonCircle6.setOnClickListener(){
                sumPassword(buttonCircle6.text.toString())
            }
            buttonCircle7.setOnClickListener(){
                sumPassword(buttonCircle7.text.toString())
            }
            buttonCircle8.setOnClickListener(){
                sumPassword(buttonCircle8.text.toString())
            }
            buttonCircle9.setOnClickListener(){
                sumPassword(buttonCircle9.text.toString())
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun sumPassword(enterPassword: String){
        if(index in 0..3){
            password += enterPassword
            index++
            setPassword(index)
        }
        if(index == 4){
            if(password == Person.password){
                startActivity(Intent(this@InputPassword, CreateMap::class.java))
            }
            else{
                Toast.makeText(this@InputPassword, "Неправильный пароль", Toast.LENGTH_LONG).show()
                index = 0
                setPassword(index)
                password = ""
            }
            val pref: SharedPreferences = getSharedPreferences("Person", Context.MODE_PRIVATE)
            pref.edit().putString("password", password.toString())
            pref.edit().apply()
        }

    }
    @SuppressLint("UseCompatLoadingForDrawables")
    fun setPassword(point: Int){
        with(binding){
            when (point){
                0 -> {
                    point1.background = getDrawable(R.drawable.point_style_stroke)
                    point2.background = getDrawable(R.drawable.point_style_stroke)
                    point3.background = getDrawable(R.drawable.point_style_stroke)
                    point4.background = getDrawable(R.drawable.point_style_stroke)
                }
                1 -> {
                    point1.background = getDrawable(R.drawable.point_style_blue)
                    point2.background = getDrawable(R.drawable.point_style_stroke)
                    point3.background = getDrawable(R.drawable.point_style_stroke)
                    point4.background = getDrawable(R.drawable.point_style_stroke)
                }
                2 -> {
                    point1.background = getDrawable(R.drawable.point_style_blue)
                    point2.background = getDrawable(R.drawable.point_style_blue)
                    point3.background = getDrawable(R.drawable.point_style_stroke)
                    point4.background = getDrawable(R.drawable.point_style_stroke)
                }
                3 -> {
                    point1.background = getDrawable(R.drawable.point_style_blue)
                    point2.background = getDrawable(R.drawable.point_style_blue)
                    point3.background = getDrawable(R.drawable.point_style_blue)
                    point4.background = getDrawable(R.drawable.point_style_stroke)
                }
                4 -> {
                    point1.background = getDrawable(R.drawable.point_style_blue)
                    point2.background = getDrawable(R.drawable.point_style_blue)
                    point3.background = getDrawable(R.drawable.point_style_blue)
                    point4.background = getDrawable(R.drawable.point_style_blue)
                }
            }
        }
    }
}