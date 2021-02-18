package com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountEatDealTotalBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.discount.DiscountFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.EatDealFragment.Companion.isEatDealLoc
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.EatDealFragment.Companion.isEatDealTotal
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.EatDealFragment.Companion.isEatDealUser
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.adapter.EatDealRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealRecyclerData
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealResultData
import java.text.DecimalFormat

class EatDealTotalFragment : BaseFragment<FragmentDiscountEatDealTotalBinding>(FragmentDiscountEatDealTotalBinding::bind, R.layout.fragment_discount_eat_deal_total), EatDealTotalFragmentView {
    lateinit var eatDealRecyclerAdapter: EatDealRecyclerAdapter
    lateinit var eatDealLayoutManager: LinearLayoutManager

    var eatDealTotalItemArray = ArrayList<EatDealResultData>()
    val eatDealTotalItemList = ArrayList<EatDealRecyclerData>()

    lateinit var eatDealTotalItemObject: EatDealRecyclerData

    var totalEatDealPage = 0
    var totalEatDealLimit = 10
    var isCanScroll = false




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        executeTotalEatDealService()

        binding.eatDealTotalLayoutSelectLoc.setOnClickListener {
            isEatDealUser = false
            isEatDealTotal = false
            isEatDealLoc = true
            (activity as MainActivity).replaceFragment(DiscountFragment())
        }

        binding.eatDealTotalLayoutMyPosition.setOnClickListener {
            isEatDealUser = true
            isEatDealTotal = false
            isEatDealLoc = false
            (activity as MainActivity).replaceFragment(DiscountFragment())
        }

    }

    fun executeTotalEatDealService() {
        EatDealTotalService(this).tryTotalGetEatDeal(page = totalEatDealPage * totalEatDealLimit, limit = totalEatDealLimit)
    }

    fun setRecyclerAdapter(){

        Handler().post {


            if (this::eatDealRecyclerAdapter.isInitialized) {
                eatDealRecyclerAdapter.notifyDataSetChanged()
            } else{
                eatDealRecyclerAdapter = EatDealRecyclerAdapter(context, eatDealTotalItemList)
                binding.eatDealTotalRecycler.apply {
                    eatDealLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    layoutManager = eatDealLayoutManager
                    setHasFixedSize(true)

                    // 무한스크롤 리스너
                    binding.eatDealTotalRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)

                            val layoutManager = eatDealLayoutManager
                            val totalItemCount: Int = layoutManager.itemCount
                            val lastVisible: Int = layoutManager.findLastCompletelyVisibleItemPosition ()

                            if (lastVisible >= totalItemCount - 2 && isCanScroll) {
                                totalEatDealPage++
                                executeTotalEatDealService()
                                isCanScroll = false
                            }

                        }
                    })

                    // 메인 리사이클러 아이템클릭 리스터
                    eatDealRecyclerAdapter.let {
                        it.setMyItemClickListener(object :
                                EatDealRecyclerAdapter.MyEatDealItemClickListener {
                            override fun onItemClick(position: Int) {

                            }
                        })
                    }

                    adapter = eatDealRecyclerAdapter
                }
            }
        }

    }

    fun setData() {
        var pickUpText = ""

        val start = totalEatDealPage * totalEatDealLimit
        var end = start + totalEatDealLimit - 1
        if (end >= eatDealTotalItemArray.size) end = eatDealTotalItemArray.size - 1
        else isCanScroll = true

        Log.d(TAG, "start: $start, end: $end")
        Log.d(TAG, "eatDealList Size: ${eatDealTotalItemArray.size}")
        Log.d(TAG, "onGetEatDealSuccess: $eatDealTotalItemArray")

        for (i in start ..  end) {
            when (eatDealTotalItemArray[i].eatDealPickUpPossible) {
                1 -> pickUpText = String.format(getString(R.string.eat_deal_can_tack_out))
                0 -> pickUpText = ""
            }

            val priceFormat = DecimalFormat("###,###,###,###")
            val beforePriceFormat = priceFormat.format(eatDealTotalItemArray[i].eatDealBeforePrice)
            val beforePrice = String.format(getString(R.string.eat_deal_before_price, beforePriceFormat))
            val afterPriceFormat = priceFormat.format(eatDealTotalItemArray[i].eatDealAfterPrice)
//            val afterPrice = String.format(getString(R.string.eat_deal_after_price, afterPriceFormat))
            val discountRate = "${eatDealTotalItemArray[i].eatDealDiscount}%"

            eatDealTotalItemObject = EatDealRecyclerData(eatDealId = eatDealTotalItemArray[i].eatDealId, firstImageUrl = eatDealTotalItemArray[i].firstImageUrl, eatDealDiscount = discountRate,
                    eatDealBeforePrice = beforePrice, eatDealAfterPrice = afterPriceFormat, eatDealName = eatDealTotalItemArray[i].eatDealName,
                    eatDealOneLine = eatDealTotalItemArray[i].eatDealOneLine, eatDealPickUpText = pickUpText)

            eatDealTotalItemList.add(eatDealTotalItemObject)
        }


        setRecyclerAdapter()
    }

    override fun onGetTotalEatDealSuccess(response: EatDealResponse, eatDealList: ArrayList<EatDealResultData>) {
        Log.d(TAG, "onGetTotalEatDealSuccess: ${response.isSuccess}")
        Log.d(TAG, "onGetTotalEatDealSuccess: ${response.code}")
        Log.d(TAG, "onGetTotalEatDealSuccess: ${response.message}")
        eatDealList.forEach { item ->
            eatDealTotalItemArray.add(item)
        }


        setData()
    }

    override fun onGetTotalEatDealFailure(message: String) {
        showCustomToast("오류 : $message")
    }


}