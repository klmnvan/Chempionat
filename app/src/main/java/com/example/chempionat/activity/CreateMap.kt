package com.example.chempionat.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.example.chempionat.R
import com.example.chempionat.databinding.ActivityCreateMapBinding
import com.example.chempionat.models.AddressModel
import com.example.chempionat.Person
import com.example.chempionat.models.PersonModel

class CreateMap : AppCompatActivity(), FragmentAddress.Listener {
    lateinit var binding: ActivityCreateMapBinding
    var index: Int = 0
    lateinit var gender: String
    var fragmentAddress = FragmentAddress(this@CreateMap)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
        binding.adress.setOnClickListener(){

        }
        binding.adress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

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

    fun textChecked()
    {
        if(binding!!.adress.text.isNotEmpty() && binding!!.inputTextPatronymic.text.isNotEmpty()
            && binding!!.inputTextSurname.text.isNotEmpty() && binding!!.inputTextBirthday.text.isNotEmpty() && index != 0){
            binding!!.buttonCreate.background = getDrawable(R.drawable.shape_button2)
            binding!!.buttonCreate.setOnClickListener{
                Person.person = PersonModel(0,binding.inputTextSurname.text.toString(),binding.adress.text.toString(),binding.inputTextPatronymic.text.toString(),
                    binding.inputTextBirthday.text.toString(), gender,"1")
                val intent = Intent(this@CreateMap, Home::class.java)
                startActivity(intent)
            }
        }
    }

    override fun setText(addressModel: AddressModel) {
        val thisAddress = addressModel.address + " kv. " + addressModel.kvartira
        binding.adress.setText(thisAddress)
        fragmentAddress.dismiss()
    }


}