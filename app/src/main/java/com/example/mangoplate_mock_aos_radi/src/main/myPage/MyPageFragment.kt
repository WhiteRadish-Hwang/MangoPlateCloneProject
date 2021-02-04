package com.example.mangoplate_mock_aos_radi.src.main.myPage

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentMyPageBinding

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.myPageToolbar.inflateMenu(R.menu.menu_my_page_toolbar)
        binding.myPageToolbar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.menu_home_toolbar_search -> {
                    showCustomToast("Clicked Bell Item")
                    Log.d(TAG, "Clicked Bell Item")
                    true
                }
                else -> false
            }
        }
    }
}