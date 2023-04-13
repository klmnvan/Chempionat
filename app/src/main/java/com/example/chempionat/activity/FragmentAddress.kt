package com.example.chempionat.activity

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chempionat.databinding.ItemFragmentAddressBinding
import com.example.chempionat.models.AddressModel

class FragmentAddress (private val listener: CreateMap): BottomSheetDialogFragment() {

    private var _binding: ItemFragmentAddressBinding? = null
    private val binding get() = _binding!!
    lateinit var address: AddressModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = ItemFragmentAddressBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.buttonConfirm.setOnClickListener(){
            listener.setText(address)
        }
    }

   interface Listener{
       fun setText(addressModel: AddressModel)
   }
}