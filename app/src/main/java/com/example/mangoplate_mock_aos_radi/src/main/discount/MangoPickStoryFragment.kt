package com.example.mangoplate_mock_aos_radi.src.main.discount

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountMangoPickStoryBinding
import com.example.mangoplate_mock_aos_radi.src.main.discount.adapter.EatDealRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.discount.adapter.MangoPickStoryRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.EatDealRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.MangoPickStoryRecyclerItems

class MangoPickStoryFragment : BaseFragment<FragmentDiscountMangoPickStoryBinding>(FragmentDiscountMangoPickStoryBinding::bind, R.layout.fragment_discount_mango_pick_story){
    lateinit var mangoPickStoryRecyclerAdapter: MangoPickStoryRecyclerAdapter
    val itemList = ArrayList<MangoPickStoryRecyclerItems>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerAdapter()
        val uri = "https://images.unsplash.com/photo-1499028344343-cd173ffc68a9?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"
        Glide.with(binding.discountMangoPickStoryImg).load(uri).placeholder(R.drawable.home_vp_img2).into(binding.discountMangoPickStoryImg)
        binding.discountTextTopImgSubTitle.text = "인천의 배달 찐 맛집은?"
        binding.discountTextTopImgTitle.text = "배달고파? 일단시켜! 방송 맛집"
    }

    fun setRecyclerAdapter(){
        initData()
        mangoPickStoryRecyclerAdapter = MangoPickStoryRecyclerAdapter(context, itemList)
        binding.mangoPickStoryRecycler.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = mangoPickStoryRecyclerAdapter
        }
    }

    private fun initData() {
        for (i in 0..5) {
            val item1 = MangoPickStoryRecyclerItems(title = "비건을 위한 맛집 6곳",
                subTitle = "비건도 맛있게 즐겨봐!",
                image = null)
            itemList.add(item1)
        }
        Log.d(ApplicationClass.TAG, "initData: $itemList")
    }

}