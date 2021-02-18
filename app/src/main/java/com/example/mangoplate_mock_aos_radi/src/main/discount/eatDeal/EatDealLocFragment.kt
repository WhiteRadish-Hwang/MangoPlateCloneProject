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
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountEatDealLocBinding
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

class EatDealLocFragment : BaseFragment<FragmentDiscountEatDealLocBinding>(FragmentDiscountEatDealLocBinding::bind, R.layout.fragment_discount_eat_deal_loc), EatDealLocFragmentView {
    lateinit var eatDealRecyclerAdapter: EatDealRecyclerAdapter
    lateinit var eatDealLayoutManager: LinearLayoutManager

    var eatDealLocItemArray = ArrayList<EatDealResultData>()
    val eatDealLocItemList = ArrayList<EatDealRecyclerData>()

    lateinit var eatDealLocItemObject: EatDealRecyclerData

    var locEatDealPage = 0
    var locEatDealLimit = 100
    var isCanScroll = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        executeLocEatDealService()

        binding.eatDealLocLayoutMyPosition.setOnClickListener {
            isEatDealUser = true
            isEatDealTotal = false
            isEatDealLoc = false
            (activity as MainActivity).replaceFragment(DiscountFragment())
        }

        binding.eatDealLocLayoutTotalView.setOnClickListener {
            isEatDealUser = false
            isEatDealTotal = true
            isEatDealLoc = false
            (activity as MainActivity).replaceFragment(DiscountFragment())
        }

    }

    fun executeLocEatDealService() {
        EatDealLocService(this).tryGetLocEatDeal(page = locEatDealPage * locEatDealLimit, limit = locEatDealLimit,
                locationfilter_sungbuk = 1, locationfilter_suyu = 2, locationfilter_nowon = 3)
    }

    fun setRecyclerAdapter(){

        Handler().post {


            if (this::eatDealRecyclerAdapter.isInitialized) {
                eatDealRecyclerAdapter.notifyDataSetChanged()
            } else{
                eatDealRecyclerAdapter = EatDealRecyclerAdapter(context, eatDealLocItemList)
                binding.eatDealLocRecycler.apply {
                    eatDealLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    layoutManager = eatDealLayoutManager
                    setHasFixedSize(true)

                    // 무한스크롤 리스너
                    binding.eatDealLocRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {
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

        val start = locEatDealPage * locEatDealLimit
        var end = start + locEatDealLimit - 1
        if (end >= eatDealLocItemArray.size) end = eatDealLocItemArray.size - 1
        else isCanScroll = true

        Log.d(TAG, "start: $start, end: $end")
        Log.d(TAG, "eatDealList Size: ${eatDealLocItemArray.size}")
        Log.d(TAG, "onGetEatDealSuccess: $eatDealLocItemArray")

        for (i in start ..  end) {
            when (eatDealLocItemArray[i].eatDealPickUpPossible) {
                1 -> pickUpText = String.format(getString(R.string.eat_deal_can_tack_out))
                0 -> pickUpText = ""
            }

            val priceFormat = DecimalFormat("###,###,###,###")
            val beforePriceFormat = priceFormat.format(eatDealLocItemArray[i].eatDealBeforePrice)
            val beforePrice = String.format(getString(R.string.eat_deal_before_price, beforePriceFormat))
            val afterPriceFormat = priceFormat.format(eatDealLocItemArray[i].eatDealAfterPrice)
            val discountRate = "${eatDealLocItemArray[i].eatDealDiscount}%"

            eatDealLocItemObject = EatDealRecyclerData(eatDealId = eatDealLocItemArray[i].eatDealId, firstImageUrl = eatDealLocItemArray[i].firstImageUrl, eatDealDiscount = discountRate,
                    eatDealBeforePrice = beforePrice, eatDealAfterPrice = afterPriceFormat, eatDealName = eatDealLocItemArray[i].eatDealName,
                    eatDealOneLine = eatDealLocItemArray[i].eatDealOneLine, eatDealPickUpText = pickUpText)

            eatDealLocItemList.add(eatDealLocItemObject)
        }


        setRecyclerAdapter()
    }

    override fun onGetLocEatDealSuccess(response: EatDealResponse, eatDealList: ArrayList<EatDealResultData>) {
        Log.d(TAG, "onGetLocEatDealSuccess: ${response.isSuccess}")
        Log.d(TAG, "onGetLocEatDealSuccess: ${response.code}")
        Log.d(TAG, "onGetLocEatDealSuccess: ${response.message}")
        eatDealList.forEach { item ->
            eatDealLocItemArray.add(item)
        }


        setData()
    }

    override fun onGetLocEatDealFailure(message: String) {

    }


}