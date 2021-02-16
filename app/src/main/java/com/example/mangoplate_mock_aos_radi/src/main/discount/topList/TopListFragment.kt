package com.example.mangoplate_mock_aos_radi.src.main.discount.topList

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountTopListBinding
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.EatDealTotalService
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.adapter.EatDealRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealRecyclerData
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealResultData
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.adapter.TopListRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.model.DiscountTopListResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.model.DiscountTopListResultData
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.model.TopListRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.home.model.TopListResultData
import java.text.DecimalFormat

class TopListFragment : BaseFragment<FragmentDiscountTopListBinding>(FragmentDiscountTopListBinding::bind, R.layout.fragment_discount_top_list), DiscountTopListFragmentView{
    lateinit var topListRecyclerAdapter: TopListRecyclerAdapter
    lateinit var topListLayoutManager: LinearLayoutManager

    // 어댑터에 넣을 리스트
    val topListItemList = ArrayList<DiscountTopListResultData>()
    // API에서 받을 리스트
    val topListItemArray = ArrayList<DiscountTopListResultData>()

    var topListPage = 0
    var topListLimit = 10
    var isCanScroll = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        executeService()

    }

    fun executeService() {
        DiscountTopListService(this).tryGetDiscountTopList(page = topListPage * topListLimit, limit = topListLimit)
    }

    fun setRecyclerAdapter(){
        topListRecyclerAdapter = TopListRecyclerAdapter(context, topListItemList)
        binding.topListRecycler.apply {
            topListLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = topListLayoutManager
            setHasFixedSize(true)

            // 무한스크롤 리스너
            binding.topListRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy);

                    val layoutManager = topListLayoutManager
                    val totalItemCount: Int = layoutManager.itemCount
                    val lastVisible: Int = layoutManager.findLastCompletelyVisibleItemPosition ();

                    if (lastVisible >= totalItemCount - 4 && isCanScroll) {
                        topListPage++
                        executeService()
                        isCanScroll = false
                    }

                }
            })

            // 메인 리사이클러 아이템클릭 리스터
            topListRecyclerAdapter.let {
                it.setMyTopListItemClickListener(object : TopListRecyclerAdapter.MyTopListItemClickListener {
                    override fun onItemClick(position: Int) {
                        // 상세페이지 클릭 이벤트 들어갈 자리
                    }
                })
            }
            adapter = topListRecyclerAdapter
        }
    }

    fun setData() {
        val start = topListPage * topListLimit
        var end = start + topListLimit - 1
        if (end >= topListItemArray.size) end = topListItemArray.size - 1
        else isCanScroll = true

        Log.d(ApplicationClass.TAG, "start: $start, end: $end")
        Log.d(ApplicationClass.TAG, "eatDealList Size: ${topListItemArray.size}")
        Log.d(ApplicationClass.TAG, "onGetEatDealSuccess: $topListItemArray")

        var topViewItemId = 0
        var topListOnTop = 0
        var idx = 0
        for (i in 1 until topListItemArray.size) {
            if (topListItemArray[idx].topListView < topListItemArray[i].topListView) {
                topViewItemId = topListItemArray[i].topListId
                idx = i
            }
            else {
                topViewItemId = topListItemArray[idx].topListId
            }
        }


        for (i in start ..  end) {
            if (topListItemArray[i].topListId == topViewItemId) topListOnTop = 1
            else topListOnTop = -1

            val topListItemObject = DiscountTopListResultData(topListId = topListItemArray[i].topListId, topListImgUrl = topListItemArray[i].topListImgUrl,
                    topListView = topListItemArray[i].topListView, updatedAt = topListItemArray[i].updatedAt, userBookMark = topListItemArray[i].userBookMark,
                    topListName = topListItemArray[i].topListName, topListOneLine = topListItemArray[i].topListOneLine, topListSortOnTop = topListOnTop)

            if (topListOnTop == 1) topListItemList.add(0, topListItemObject)
            else topListItemList.add(topListItemObject)
        }

        setRecyclerAdapter()
    }


    override fun onGetTopListSuccess(response: DiscountTopListResponse, topListList: ArrayList<DiscountTopListResultData>) {
        Log.d(ApplicationClass.TAG, "onGetTopListSuccess: ${response.isSuccess}")
        Log.d(ApplicationClass.TAG, "onGetTopListSuccess: ${response.code}")
        Log.d(ApplicationClass.TAG, "onGetTopListSuccess: ${response.message}")
        topListList.forEach { item ->
            topListItemArray.add(item)
        }


        setData()
    }

    override fun onGetTopListFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}