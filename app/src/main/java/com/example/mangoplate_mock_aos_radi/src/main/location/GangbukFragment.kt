package com.example.mangoplate_mock_aos_radi.src.main.location

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountTopListBinding
import com.example.mangoplate_mock_aos_radi.databinding.FragmentLocSelectGangbukBinding
import com.example.mangoplate_mock_aos_radi.src.main.discount.adapter.TopListRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.TopListRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.location.adapter.LocSelectRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.location.model.LocSelectRecyclerItems

class GangbukFragment : BaseFragment<FragmentLocSelectGangbukBinding>(FragmentLocSelectGangbukBinding::bind, R.layout.fragment_loc_select_gangbuk){
    lateinit var topListRecyclerAdapter: LocSelectRecyclerAdapter
    val itemList = ArrayList<LocSelectRecyclerItems>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerAdapter()
    }

    fun setRecyclerAdapter(){
        initData()
        topListRecyclerAdapter = LocSelectRecyclerAdapter(context, itemList)
        binding.gangbukRecycler.apply {
            layoutManager = GridLayoutManager(context,2, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = topListRecyclerAdapter
        }
    }

    private fun initData() {
        val locList = arrayListOf<String>("전체", "건대/군자/광진", "광화문", "노원구", "대학로/혜화",
                "동대문구", "동부이촌동", "마포/공덕", "명동/남산", "삼청/인사",
                "상암/성산", "서대문구", "성북구", "수유/도봉/강북", "시청/남대문",
                "신촌/이대", "연남동", "왕십리/성동","용산/숙대", "은평구",
                "이태원/한남동", "종로", "중구", "중랑구", "합정/망원", "홍대")
        val items = ArrayList<LocSelectRecyclerItems>()

        for (loc in locList) items.add(LocSelectRecyclerItems(location = loc))

        for (item in items) itemList.add(item)
    }
}