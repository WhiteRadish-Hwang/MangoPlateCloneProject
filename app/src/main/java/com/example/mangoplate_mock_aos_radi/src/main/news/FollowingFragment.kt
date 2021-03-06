package com.example.mangoplate_mock_aos_radi.src.main.news

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsFollowingBinding
import com.example.mangoplate_mock_aos_radi.src.main.news.model.FollowingRecyclerItems

class FollowingFragment : BaseFragment<FragmentNewsFollowingBinding>(FragmentNewsFollowingBinding::bind, R.layout.fragment_news_following){
    val itemList = ArrayList<FollowingRecyclerItems>()

    //    fun setRecyclerAdapter(){
//        initData()
//        binding.followingRecycler.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            setHasFixedSize(true)
//            adapter = FollowingRecyclerAdapter(context, itemList)
//        }
//    }

    private fun initData() {
        for (i in 0..5) {
            val item1 = FollowingRecyclerItems(title = "[신논현] 앙트레블",
                subTitle = "과카몰리&토마토 오픈샌드위치",
                image = "https://images.unsplash.com/photo-1499028344343-cd173ffc68a9?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80")
            itemList.add(item1)
        }
        Log.d(ApplicationClass.TAG, "initData: $itemList")
    }

}
