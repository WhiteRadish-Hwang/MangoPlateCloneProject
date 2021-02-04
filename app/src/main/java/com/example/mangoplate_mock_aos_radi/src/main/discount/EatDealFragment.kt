package com.example.mangoplate_mock_aos_radi.src.main.discount

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountEatDealBinding
import com.example.mangoplate_mock_aos_radi.src.main.discount.adapter.EatDealRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.EatDealRecyclerItems

class EatDealFragment : BaseFragment<FragmentDiscountEatDealBinding>(FragmentDiscountEatDealBinding::bind, R.layout.fragment_discount_eat_deal){
    lateinit var eatDealRecyclerAdapter: EatDealRecyclerAdapter
    val itemList = ArrayList<EatDealRecyclerItems>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerAdapter()
    }

    fun setRecyclerAdapter(){
        initData()
        eatDealRecyclerAdapter = EatDealRecyclerAdapter(context, itemList)
        binding.eatDealRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = eatDealRecyclerAdapter
        }
    }

    private fun initData() {
        for (i in 0..5) {
            val item1 = EatDealRecyclerItems(title = "[신논현] 앙트레블",
                    subTitle = "과카몰리&토마토 오픈샌드위치",
                    canTakeout = "포장 & 픽업 가능한 EAT딜입니다.",
                    price = "12000", discountRate = "10", newAndHot = "NEW" ,
                    image = "https://images.unsplash.com/photo-1499028344343-cd173ffc68a9?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80")
            itemList.add(item1)
        }
        Log.d(TAG, "initData: $itemList")
    }


}