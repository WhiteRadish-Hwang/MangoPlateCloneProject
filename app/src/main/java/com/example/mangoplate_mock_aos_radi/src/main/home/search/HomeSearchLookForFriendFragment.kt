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

class HomeSearchLookForFriendFragment : BaseFragment<FragmentHomeSearchRecommendBinding>(FragmentHomeSearchRecommendBinding::bind, R.layout.fragment_home_search_recommend){
    val recommendDataList = ArrayList<HomeSearchRecyclerItems>()
    lateinit var homeSearchRecommendRecyclerAdapter: HomeSearchRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerAdapter()
    }

    fun setRecyclerAdapter(){
        initRecommendData()
        homeSearchRecommendRecyclerAdapter = HomeSearchRecyclerAdapter(context, recommendDataList)
        binding.homeSearchRecommendRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = homeSearchRecommendRecyclerAdapter
        }
    }

    fun initRecommendData() {
//        val item1 = HomeSearchRecyclerItems(word = "EAT딜있는식당")
//        val item2 = HomeSearchRecyclerItems(word = "2021망고플레이트인기맛집")
//        val item3 = HomeSearchRecyclerItems(word = "미쉐린")
//        val item4 = HomeSearchRecyclerItems(word = "분식")
//        val item5 = HomeSearchRecyclerItems(word = "수제버거")
//        val item6 = HomeSearchRecyclerItems(word = "스시")
//        val itemList = arrayListOf<HomeSearchRecyclerItems>(item1, item2, item3, item4, item5, item6)
//
//        for (item in itemList) recommendDataList.add(item)

    }


}