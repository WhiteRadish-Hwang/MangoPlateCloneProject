package com.example.mangoplate_mock_aos_radi.src.main.home.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountEatDealBinding
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeSearchRecommendBinding
import com.example.mangoplate_mock_aos_radi.src.main.discount.adapter.EatDealRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.EatDealRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.home.adapter.HomeSearchRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.home.model.HomeSearchRecyclerItems

class HomeSearchRecentFragment : BaseFragment<FragmentHomeSearchRecommendBinding>(FragmentHomeSearchRecommendBinding::bind, R.layout.fragment_home_search_recommend){
    val recentDataList = ArrayList<HomeSearchRecyclerItems>()
    lateinit var homeSearchRecommendRecyclerAdapter: HomeSearchRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerAdapter()
    }

    fun setRecyclerAdapter(){
        initRecommendData()
        homeSearchRecommendRecyclerAdapter = HomeSearchRecyclerAdapter(context, recentDataList)
        binding.homeSearchRecommendRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = homeSearchRecommendRecyclerAdapter
        }
    }

    fun initRecommendData() {
        val item1 = HomeSearchRecyclerItems(word = "EAT딜있는식당")

        val itemList = arrayListOf<HomeSearchRecyclerItems>(item1)

        for (item in itemList) recentDataList.add(item)

    }
}