package com.example.mangoplate_mock_aos_radi.src.main.add

import android.os.Bundle
import android.view.View
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentAddBinding

class AddFragment: BaseFragment<FragmentAddBinding>(FragmentAddBinding::bind, R.layout.fragment_add){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}