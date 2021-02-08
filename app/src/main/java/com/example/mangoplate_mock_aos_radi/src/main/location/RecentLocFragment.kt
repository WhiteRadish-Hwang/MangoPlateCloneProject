package com.example.mangoplate_mock_aos_radi.src.main.location

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountTopListBinding
import com.example.mangoplate_mock_aos_radi.src.main.discount.adapter.TopListRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.TopListRecyclerItems

class RecentLocFragment : BaseFragment<FragmentDiscountTopListBinding>(FragmentDiscountTopListBinding::bind, R.layout.fragment_discount_top_list){
    lateinit var topListRecyclerAdapter: TopListRecyclerAdapter
    val itemList = ArrayList<TopListRecyclerItems>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerAdapter()
    }

    fun setRecyclerAdapter(){
        initData()
        topListRecyclerAdapter = TopListRecyclerAdapter(context, itemList)
        binding.topListRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = topListRecyclerAdapter
        }
    }

    private fun initData() {
        for (i in 0..5) {
            val item1 = TopListRecyclerItems(title = "햄버거 맛집 베스트 50곳",
                subTitle = "햄버거는 언제나 제일 맛있는 법!",
                viewCount = "123,123",
                uplosdDateAgo = "1일 전",
                image = "https://images.unsplash.com/photo-1499028344343-cd173ffc68a9?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80")
            itemList.add(item1)
        }
        Log.d(ApplicationClass.TAG, "initData: $itemList")
    }
}