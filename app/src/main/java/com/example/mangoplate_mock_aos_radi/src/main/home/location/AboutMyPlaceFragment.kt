package com.example.mangoplate_mock_aos_radi.src.main.home.location

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountTopListBinding
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.adapter.TopListRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.model.TopListRecyclerItems

class AboutMyPlaceFragment : BaseFragment<FragmentDiscountTopListBinding>(FragmentDiscountTopListBinding::bind, R.layout.fragment_discount_top_list){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}