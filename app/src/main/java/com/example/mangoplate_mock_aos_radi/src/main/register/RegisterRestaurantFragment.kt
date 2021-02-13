package com.example.mangoplate_mock_aos_radi.src.main.register

import android.os.Bundle
import android.view.View
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentRegisterRestaurantBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity

class RegisterRestaurantFragment: BaseFragment<FragmentRegisterRestaurantBinding>(FragmentRegisterRestaurantBinding::bind, R.layout.fragment_register_restaurant){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.registerImgArrow.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

    }
}