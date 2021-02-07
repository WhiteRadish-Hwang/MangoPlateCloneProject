package com.example.mangoplate_mock_aos_radi.src.main.home

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.FragmentManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeBinding
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeSearchFrameBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity

class HomeSearchFragment: BaseFragment<FragmentHomeSearchFrameBinding>(FragmentHomeSearchFrameBinding::bind, R.layout.fragment_home_search_frame) {

    var isOpenSearchDrawer = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView2.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack("search", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

    }
}