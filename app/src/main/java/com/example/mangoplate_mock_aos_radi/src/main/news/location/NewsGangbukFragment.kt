package com.example.mangoplate_mock_aos_radi.src.main.news.location

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentLocSelectGangbukBinding
import com.example.mangoplate_mock_aos_radi.src.main.home.location.adapter.LocSelectRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.home.location.model.LocSelectRecyclerItems

class NewsGangbukFragment : BaseFragment<FragmentLocSelectGangbukBinding>(FragmentLocSelectGangbukBinding::bind, R.layout.fragment_loc_select_gangbuk){
    companion object {
        var isNewsLocCheck: Boolean = false
        var newsLocList = ArrayList<String>()
    }

    lateinit var newsLocSelectRecyclerAdapter: LocSelectRecyclerAdapter
    val itemList = ArrayList<LocSelectRecyclerItems>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerAdapter()
    }

    fun setRecyclerAdapter(){
        initData()
        newsLocSelectRecyclerAdapter = LocSelectRecyclerAdapter(context, itemList)
        binding.gangbukRecycler.apply {
            layoutManager = GridLayoutManager(context,2, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            // 아이템 클릭 리스너
            newsLocSelectRecyclerAdapter.let {
                it.setMyLocItemClickListener(object : LocSelectRecyclerAdapter.MyLocItemClickListener {
                    override fun onItemClick(position: Int) {
                        showCustomToast("${itemList[position]}")
                    }
                })
            }

            adapter = newsLocSelectRecyclerAdapter
        }

    }

    private fun initData() {
        val locItemList = arrayListOf<String>("전체", "건대/군자/광진", "광화문", "노원구", "대학로/혜화",
                "동대문구", "동부이촌동", "마포/공덕", "명동/남산", "삼청/인사",
                "상암/성산", "서대문구", "성북구", "수유/도봉/강북", "시청/남대문",
                "신촌/이대", "연남동", "왕십리/성동","용산/숙대", "은평구",
                "이태원/한남동", "종로", "중구", "중랑구", "합정/망원", "홍대")
        val items = ArrayList<LocSelectRecyclerItems>()

        for (locItem in locItemList) {
            for (loc in newsLocList) if (locItem == loc) items.add(LocSelectRecyclerItems(location = loc, isSelected = true, isHomeLoc = false))

            items.add(LocSelectRecyclerItems(location = locItem, isSelected = false, isHomeLoc = false))
        }

        for (item in items) itemList.add(item)

        for (item in items) itemList.add(item)
        Log.d(ApplicationClass.TAG, "itemList: $itemList")
    }
}