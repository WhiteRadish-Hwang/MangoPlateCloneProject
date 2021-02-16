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
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountEatDealBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.discount.DiscountFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.adapter.EatDealRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealRecyclerData
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealResultData
import java.text.DecimalFormat

class EatDealFragment : BaseFragment<FragmentDiscountEatDealBinding>(FragmentDiscountEatDealBinding::bind, R.layout.fragment_discount_eat_deal), EatDealFragmentView {
    companion object {
        var isEatDealUser: Boolean = true
        var isEatDealTotal: Boolean = false
        var isEatDealLoc: Boolean = false
    }

    lateinit var eatDealRecyclerAdapter: EatDealRecyclerAdapter
    lateinit var eatDealLayoutManager: LinearLayoutManager

    var eatDealItemArray = ArrayList<EatDealResultData>()
    val eatDealItemList = ArrayList<EatDealRecyclerData>()

    lateinit var eatDealItemObject: EatDealRecyclerData

    var eatDealPage = 0
    var eatDealLimit = 10
    var isCanScroll = false



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        executeService()

        binding.eatDealLayoutSelectLoc.setOnClickListener {
            isEatDealUser = false
            isEatDealTotal = false
            isEatDealLoc = true
            (activity as MainActivity).replaceFragment(DiscountFragment())
        }

        binding.eatDealTextTotalView.setOnClickListener {
            isEatDealUser = false
            isEatDealTotal = true
            isEatDealLoc = false
            (activity as MainActivity).replaceFragment(DiscountFragment())
        }

    }

    fun executeService() {
        EatDealService(this).tryGetEatDeal(page = eatDealPage * eatDealLimit, limit = eatDealLimit, userlatitude = 37.6511723f, userlongtitude = 127.0481563f)
    }

    fun setRecyclerAdapter(){

        Handler().post() {


            if (this::eatDealRecyclerAdapter.isInitialized) {
                eatDealRecyclerAdapter.notifyDataSetChanged()
            } else{
                eatDealRecyclerAdapter = EatDealRecyclerAdapter(context, eatDealItemList)
                binding.eatDealRecycler.apply {
                    eatDealLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    layoutManager = eatDealLayoutManager
                    setHasFixedSize(true)

                    // 무한스크롤 리스너
                    binding.eatDealRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy);

//                            val layoutManager = eatDealLayoutManager
//                            val totalItemCount: Int = layoutManager.itemCount
//                            val lastVisible: Int = layoutManager.findLastCompletelyVisibleItemPosition ();
//
//                            if (lastVisible >= totalItemCount - 2 && isCanScroll) {
//                                eatDealPage++
//                                executeService()
//                                isCanScroll = false
//                            }
//
//                            if (lastVisible >= totalItemCount - 1) {
//                                binding.eatDealTextTotalView.visibility = View.VISIBLE
//                            } else{
//                                binding.eatDealTextTotalView.visibility = View.GONE
//                            }
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

        val start = eatDealPage * eatDealLimit
        var end = start + eatDealLimit - 1
        if (end >= eatDealItemArray.size) end = eatDealItemArray.size - 1
        else isCanScroll = true

        Log.d(TAG, "start: $start, end: $end")
        Log.d(TAG, "eatDealList Size: ${eatDealItemArray.size}")
        Log.d(TAG, "onGetEatDealSuccess: $eatDealItemArray")

        for (i in start ..  end) {
            when (eatDealItemArray[i].eatDealPickUpPossible) {
                1 -> pickUpText = String.format(getString(R.string.eat_deal_can_tack_out))
                0 -> pickUpText = ""
            }

            val priceFormat = DecimalFormat("###,###,###,###")
            val beforePriceFormat = priceFormat.format(eatDealItemArray[i].eatDealBeforePrice)
            val beforePrice = String.format(getString(R.string.eat_deal_before_price, beforePriceFormat))
            val afterPriceFormat = priceFormat.format(eatDealItemArray[i].eatDealAfterPrice)
//            val afterPrice = String.format(getString(R.string.eat_deal_after_price, afterPriceFormat))
            val discountRate = "${eatDealItemArray[i].eatDealDiscount}%"

            eatDealItemObject = EatDealRecyclerData(eatDealId = eatDealItemArray[i].eatDealId, firstImageUrl = eatDealItemArray[i].firstImageUrl, eatDealDiscount = discountRate,
                    eatDealBeforePrice = beforePrice, eatDealAfterPrice = afterPriceFormat, eatDealName = eatDealItemArray[i].eatDealName,
                    eatDealOneLine = eatDealItemArray[i].eatDealOneLine, eatDealPickUpText = pickUpText)

            eatDealItemList.add(eatDealItemObject)
        }


        setRecyclerAdapter()
    }

    override fun onGetEatDealSuccess(response: EatDealResponse, eatDealList: ArrayList<EatDealResultData>) {
        Log.d(TAG, "onGetEatDealSuccess: ${response.isSuccess}")
        Log.d(TAG, "onGetEatDealSuccess: ${response.code}")
        Log.d(TAG, "onGetEatDealSuccess: ${response.message}")

        eatDealList.forEach { item ->
            eatDealItemArray.add(item)
        }

        setData()

    }

    override fun onGetEatDealFailure(message: String) {
        showCustomToast("오류 : $message")
    }


}