package com.example.mangoplate_mock_aos_radi.src.main.visited

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.config.SharedPreferenced
import com.example.mangoplate_mock_aos_radi.databinding.FragmentVisitedBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFragment
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFragment.Companion.homeDetailsKey
import com.example.mangoplate_mock_aos_radi.src.main.visited.model.GetVisitedResponse
import com.example.mangoplate_mock_aos_radi.src.main.visited.model.GetVisitedResultData
import com.example.mangoplate_mock_aos_radi.src.main.visited.model.PostVisitedResponse
import kotlin.properties.Delegates

class VisitedFragment:BaseFragment<FragmentVisitedBinding>(FragmentVisitedBinding::bind, R.layout.fragment_visited), VisitedFragmentView {
    companion object{
        const val visitedRestaurantIdKey = "visitedRestaurantIdKey"
    }

    lateinit var visitedRestaurantArea: String
    lateinit var visitedRestaurantMessage: String
    lateinit var visitedRestaurantName: String
    lateinit var visitedFirstImageUrl: String
    lateinit var visitedRestaurantFilter: String
    var visitedRestaurantView by Delegates.notNull<Int>()
    var visitedReviewCount by Delegates.notNull<Int>()
    var visitedRestaurantId by Delegates.notNull<Int>()

    var visitedIsLock: Boolean = false
    var visitedLockStatus: Int = 1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        visitedRestaurantId = arguments?.getInt(visitedRestaurantIdKey)!!

        VisitedService(this).tryGetVisited(visitedRestaurantId)

        binding.visitedImgBack.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        binding.visitedImgLock.setOnClickListener {
            visitedIsLock = !visitedIsLock
            when (visitedIsLock) {
                true -> {
                    binding.visitedImgLock.setImageResource(R.drawable.visited_lock)
                    visitedLockStatus = 2
                }
                false -> {
                    binding.visitedImgLock.setImageResource(R.drawable.visited_unlock)
                    visitedLockStatus = 1
                }
            }

        }

        binding.visitedBtnAddApply.setOnClickListener {
            VisitedService(this).tryPostVisitedApply(restaurantId = visitedRestaurantId, status = visitedLockStatus)
        }

    }

    fun viewDataHold() {
        val ul_restaurantName = String.format(getString(R.string.review_restaurant_name_val, visitedRestaurantName))
        val ul_restaurantLoc = String.format(getString(R.string.review_restaurant_loc_val, visitedRestaurantArea))
        binding.visitedTextRestaurantName.text = ul_restaurantName
        binding.visitedTextRestaurantLoc.text = ul_restaurantLoc
        binding.visitedImgMessage.text = visitedRestaurantMessage
        binding.visitedTextRestaurantNameInContent.text = visitedRestaurantName
        binding.visitedTextRestaurantAreaInContent.text = visitedRestaurantArea
        binding.visitedTextRestaurantFilterInContent.text = visitedRestaurantFilter
        binding.visitedTextUserViewCount.text = visitedRestaurantView.toString()
        binding.visitedTextUserReviewCount.text = visitedReviewCount.toString()
    }

    override fun onGetVisitedSuccess(response: GetVisitedResponse, result: ArrayList<GetVisitedResultData>) {
        Log.d(TAG, "onGetVisitedSuccess: ${result}")
        Log.d(TAG, "onGetVisitedSuccess: ${response.isSuccess}")
        Log.d(TAG, "onGetVisitedSuccess: ${response.code}")
        Log.d(TAG, "onGetVisitedSuccess: ${response.message}")
        showCustomToast("visited Called")

        when (response.code) {
            1000 -> {
                for (i in 0 until result.size){
                    visitedRestaurantArea = result[i].area
                    visitedRestaurantName = result[i].restaurantName
                    visitedRestaurantMessage = result[i].message
                    visitedFirstImageUrl = result[i].firstImageUrl
                    visitedRestaurantFilter = result[i].restaurantFilter
                    visitedRestaurantView = result[i].restaurantView
                    visitedReviewCount = result[i].reviewCount
                }
                viewDataHold()
            }
            4002 -> {
                showCustomToast("가봤어요는 하루에 한 번만 가능합니다!")
                (activity as MainActivity).onBackPressed()
            }
        }

    }

    override fun onGetVisitedFailure(message: String) {
        showCustomToast("오류: $message")
    }

    override fun onPostVisitedApplySuccess(response: PostVisitedResponse) {
        Log.d(TAG, "onGetVisitedSuccess: ${response.isSuccess}")
        Log.d(TAG, "onGetVisitedSuccess: ${response.code}")
        Log.d(TAG, "onGetVisitedSuccess: ${response.message}")

        when (response.code) {
            1000 -> {
                showCustomToast("가봤어요 등록 완료!")
                Log.d(TAG, "onViewCreated: 가봤어요 추후 페이지 전환 예정")
                (activity as MainActivity).replaceFragmentInSubFrame(HomeDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putInt(homeDetailsKey, visitedRestaurantId)
                    }
                })
            }
            4002 -> {
                showCustomToast("가봤어요는 하루에 한 번만 가능합니다!")
                (activity as MainActivity).onBackPressed()
            }
        }


    }

    override fun onPostVisitedApplyFailure(message: String) {
        showCustomToast("가봤어요 등록 실패: $message")
        (activity as MainActivity).onBackPressed()
    }
}