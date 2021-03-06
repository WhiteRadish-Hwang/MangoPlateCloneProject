package com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountEatDealDetailsBinding
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountEatDealDetailsImgSlideSrcBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.*
import com.example.mangoplate_mock_aos_radi.src.main.home.HomeFragment
import com.example.mangoplate_mock_aos_radi.src.main.home.HomeImageSlideFragment
import com.example.mangoplate_mock_aos_radi.src.payment.PaymentWebActivity
import com.google.android.material.appbar.AppBarLayout
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.properties.Delegates

class EatDealDetailsFragment: BaseFragment<FragmentDiscountEatDealDetailsBinding>(FragmentDiscountEatDealDetailsBinding::bind, R.layout.fragment_discount_eat_deal_details), EatDealDetailsFragmentView {
    companion object {
        const val eatDealDetailsDealIdKey = "eatDealDetailsDealIdKey"
        const val DEAL_NAME = "DEAL_NAME"
        const val BUYER_NAME = "BUYER_NAME"
        const val MERCHANT_UID = "MERCHANT_UID"
        const val AMOUNT = "AMOUNT"
    }

    var eatDealDetailsDealId by Delegates.notNull<Int>()

    var eatDealImgsArrayList = ArrayList<EatDealDetailsImagesItems>()
    var eatDealInfoArrayList = ArrayList<EatDealDetailsInfoItems>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eatDealDetailsDealId = arguments?.getInt(eatDealDetailsDealIdKey) as Int

        executeEatDealDetailsService()

        binding.dealDetailsImgArrowWhite.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
        binding.dealDetailsImgArrowOrange.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        binding.dealDetailsBtnAddApply.setOnClickListener {
            val body: PostPaymentsRequest = PostPaymentsRequest(eatDealId = eatDealDetailsDealId)
            EatDealDetailsService(this).tryPostPayments(body)

        }

        
        binding.dealDetailsLayoutAppBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) { // ????????????
                binding.dealDetailsToolbar.setBackgroundResource(R.color.white)
                binding.dealDetailsToolbarLayout.setBackgroundResource(R.drawable.home_toolbar_underline)
                binding.dealDetailsTextResName.visibility = View.VISIBLE
                binding.dealDetailsTextResName.animation = AnimationUtils.loadAnimation(context, R.anim.alpha_enter)
                binding.dealDetailsImgArrowOrange.visibility = View.VISIBLE
                binding.dealDetailsImgArrowOrange.animation = AnimationUtils.loadAnimation(context, R.anim.alpha_enter)
            } else {// ????????????
                binding.dealDetailsToolbar.background = null
                binding.dealDetailsToolbarLayout.background = null
                binding.dealDetailsTextResName.visibility = View.GONE
                binding.dealDetailsTextResName.animation = AnimationUtils.loadAnimation(context, R.anim.alpha_exit)
                binding.dealDetailsImgArrowOrange.visibility = View.GONE
                binding.dealDetailsImgArrowOrange.animation = AnimationUtils.loadAnimation(context, R.anim.alpha_exit)
            }
        })

        
    }

    private fun executeEatDealDetailsService() {
        showLoadingDialog(context!!)
        EatDealDetailsService(this).tryGetEatDealDetails(eatDealDetailsDealId)
    }

    private fun viewBindData() {
        //infoList??? ????????? ???????????? 1?????? ?????? ????????? ????????? ?????? ????????? 0 ?????? ????????? ??????

        binding.dealDetailsTextResName.text = eatDealInfoArrayList[0].eatDealName

        binding.dealDetailsIndicatorTextMessage.text = eatDealInfoArrayList[0].message
        binding.dealDetailsTextEatDealName.text = eatDealInfoArrayList[0].eatDealName
        binding.dealDetailsTextOneLine.text = eatDealInfoArrayList[0].eatDealOneLine
        binding.dealDetailsTextTerm.text = eatDealInfoArrayList[0].eatDealTerm

        val priceFormat = DecimalFormat("###,###,###,###")
        val beforePriceFormat = priceFormat.format(eatDealInfoArrayList[0].eatDealBeforePrice)
        val beforePrice = String.format(getString(R.string.eat_deal_before_price, beforePriceFormat))
        val afterPriceFormat = priceFormat.format(eatDealInfoArrayList[0].eatDealAfterPrice)
        val afterPrice = String.format(getString(R.string.eat_deal_after_price, afterPriceFormat))
        val discountRate = "${eatDealInfoArrayList[0].eatDealDiscount}%"
        binding.dealDetailsTextBeforePrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        binding.dealDetailsTextBeforePrice.text = beforePrice
        binding.dealDetailsTextAfterPrice.text = afterPrice
        binding.dealDetailsTextDiscountRate.text = discountRate

        when (eatDealInfoArrayList[0].eatDealPickUpPossible) {
            1 -> {
                binding.dealDetailsTextPickUpPossible.visibility = View.VISIBLE
                binding.dealDetailsTextPickUpPossible.text = getString(R.string.deal_text_pick_up_possible)
            }
            else -> {
                binding.dealDetailsTextPickUpPossible.visibility = View.GONE
                binding.dealDetailsTextPickUpPossible.text = ""
            }
        }


        binding.dealDetailsTextResInfo.text = eatDealInfoArrayList[0].restaurantInfo
        binding.dealDetailsTextMenuInfo.text = eatDealInfoArrayList[0].menuInfo
        binding.dealDetailsTextNoticeInfo.text = eatDealInfoArrayList[0].noticeInfo
        binding.dealDetailsTextHowToUseInfo.text = eatDealInfoArrayList[0].howToUseInfo
        binding.dealDetailsTextRefundPolicyInfo.text = eatDealInfoArrayList[0].refundPolicyInfo
    }

    

    private inner class DealDetailsImageSlidePagerAdapter(fragment: EatDealDetailsFragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = eatDealImgsArrayList.size

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> EatDealDetailsImageSlideFragment(eatDealImgsArrayList[position].eatDealImgUrl)
                1 -> EatDealDetailsImageSlideFragment(eatDealImgsArrayList[position].eatDealImgUrl)
                2 -> EatDealDetailsImageSlideFragment(eatDealImgsArrayList[position].eatDealImgUrl)
                3 -> EatDealDetailsImageSlideFragment(eatDealImgsArrayList[position].eatDealImgUrl)
                else -> EatDealDetailsImageSlideFragment(eatDealImgsArrayList[eatDealImgsArrayList.size - 1].eatDealImgUrl)
            }
        }
    }

    override fun onGetEatDealDetailsSuccess(response: EatDealDetailsResponse, imagesList: ArrayList<EatDealDetailsImagesItems>, infoList: ArrayList<EatDealDetailsInfoItems>) {
        dismissLoadingDialog()
        Log.d(TAG, "onGetEatDealDetailsSuccess: ${response.isSuccess}")
        Log.d(TAG, "onGetEatDealDetailsSuccess: ${response.code}")
        Log.d(TAG, "onGetEatDealDetailsSuccess: ${response.message}")
        Log.d(TAG, "onGetEatDealDetailsSuccess: $imagesList")
        Log.d(TAG, "onGetEatDealDetailsSuccess: $infoList")

        eatDealImgsArrayList = imagesList

        eatDealInfoArrayList = infoList




        val pagerAdapter = DealDetailsImageSlidePagerAdapter(this)
        binding.dealDetailsVp.adapter = pagerAdapter
        binding.dealDetailsVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.dealDetailsIndicatorTextPageTotal.text = eatDealImgsArrayList.size.toString()
                binding.dealDetailsIndicatorTextPage.text = (position + 1).toString()
            }
        })

        viewBindData()


    }

    override fun onGetEatDealDetailsFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("?????? : $message")
    }

    override fun onPostPaymentsSuccess(response: PaymentsResponse, merchant_uid: Int, buyerName: String) {
        dismissLoadingDialog()
        Log.d(TAG, "onPostPaymentsSuccess: ${response.isSuccess}")
        Log.d(TAG, "onPostPaymentsSuccess: ${response.code}")
        Log.d(TAG, "onPostPaymentsSuccess: ${response.message}")
        Log.d(TAG, "onPostPaymentsSuccess: ${response.merchant_uid}")
        Log.d(TAG, "onPostPaymentsSuccess: ${response.buyerName}")

        val intent = Intent(context, PaymentWebActivity::class.java)
        intent.putExtra(MERCHANT_UID, merchant_uid)
        intent.putExtra(BUYER_NAME, buyerName)
        intent.putExtra(AMOUNT, eatDealInfoArrayList[0].eatDealAfterPrice)
        intent.putExtra(DEAL_NAME, eatDealInfoArrayList[0].eatDealName)
        startActivity(intent)
    }

    override fun onPostPaymentsFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("?????? : $message")
    }


}