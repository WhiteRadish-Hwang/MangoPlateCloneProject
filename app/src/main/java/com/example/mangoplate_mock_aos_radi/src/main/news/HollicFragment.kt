package com.example.mangoplate_mock_aos_radi.src.main.news

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.config.SharedPreferenced
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsHollicBinding
import com.example.mangoplate_mock_aos_radi.src.main.discount.adapter.EatDealRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.EatDealRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.adapter.HollicRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.news.model.HollicRecyclerItems

class HollicFragment : BaseFragment<FragmentNewsHollicBinding>(FragmentNewsHollicBinding::bind, R.layout.fragment_news_hollic){
    val itemList = ArrayList<HollicRecyclerItems>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerAdapter()
    }

    fun setRecyclerAdapter(){
        initData()
        binding.hollicRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = HollicRecyclerAdapter(context, itemList)
        }
    }

    private fun initData() {
        for (i in 0..5) {
            val item1 = HollicRecyclerItems(title = "[신논현] 앙트레블",
                subTitle = "과카몰리&토마토 오픈샌드위치",

                image = "https://images.unsplash.com/photo-1499028344343-cd173ffc68a9?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80")
            itemList.add(item1)
        }
        Log.d(ApplicationClass.TAG, "initData: $itemList")
    }

}