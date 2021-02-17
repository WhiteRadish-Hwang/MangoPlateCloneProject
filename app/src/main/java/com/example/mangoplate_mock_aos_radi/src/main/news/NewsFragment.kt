package com.example.mangoplate_mock_aos_radi.src.main.news

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.NEWS_LOC_LIST
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.config.SharedPreferenced
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.news.HolicFragment.Companion.holicListKey
import com.example.mangoplate_mock_aos_radi.src.main.news.TotalFragment.Companion.totalListKey
import com.example.mangoplate_mock_aos_radi.src.main.news.location.NewsGangbukFragment.Companion.newsLocList
import com.example.mangoplate_mock_aos_radi.src.main.news.location.NewsLocationSelectFragment
import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData
import com.google.android.material.tabs.TabLayoutMediator

class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::bind, R.layout.fragment_news), NewsFragmentView{
    companion object {
        var expressionList = ArrayList<String>()
    }

    var expressionCount = 0

    var newsLocationFilter_sungBuk: Int = 0
    var newsLocationFilter_suYu: Int = 0
    var newsLocationFilter_noWon: Int = 0

    var expression_great: Int = 0
    var expression_good: Int = 0
    var expression_bad: Int = 0

    var totalArgList = ArrayList<TotalReviewResultData>()
    var holicArgList = ArrayList<TotalReviewResultData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 필터 변수 받기
        SharedPreferenced.getArrayStringItem(ApplicationClass.NEWS_EXPRESSION_LIST)?.let { expressionList = it }
        Log.d(TAG, "expressionList: $expressionList")

        for (expression in expressionList) {
            when (expression) {
                "great" -> {
                    expression_great = 2
                }
                "good" -> {
                    expression_good = 1
                }
                "bad" -> {
                    expression_bad = -1
                }
            }
        }
        if (expressionList.isEmpty()) {
            expression_great = 2
            expression_good = 0
            expression_bad = 0
        }


        // 지역 변수 받기
        SharedPreferenced.getArrayStringItem(NEWS_LOC_LIST).let { newsLocList = it!! }
        Log.d(TAG, "newsLocList: ${newsLocList}")

        for (loc in newsLocList) {
            when (loc) {
                "성북구" -> {
                    newsLocationFilter_sungBuk = 1
                }
                "수유/도봉/강북" -> {
                    newsLocationFilter_suYu = 2
                }
                "노원구" -> {
                    newsLocationFilter_noWon = 3
                }
                "전체" -> {
                    newsLocationFilter_sungBuk = 1
                    newsLocationFilter_suYu = 2
                    newsLocationFilter_noWon = 3
                }
            }
        }
        Log.d(TAG, "newsLocationFilter_sungBuk: $newsLocationFilter_sungBuk")
        Log.d(TAG, "newsLocationFilter_suYu: $newsLocationFilter_suYu")
        Log.d(TAG, "newsLocationFilter_noWon: $newsLocationFilter_noWon")
        Log.d(TAG, "isEmpty: ${newsLocList.isEmpty()}")

        if (newsLocList.isEmpty()) {
            newsLocationFilter_sungBuk = 1
            newsLocationFilter_suYu = 2
            newsLocationFilter_noWon = 3
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        executeService()

        // 탭 레이아웃 아래 메인 내용 뷰 바인딩
        frameViewBind()

        // 지역 바텀시트 프래그먼트 실행
        binding.newsToolbarTvLocChangedText.setOnClickListener {
            val newsLocSelectFragment = NewsLocationSelectFragment()
            newsLocSelectFragment.show((activity as MainActivity).supportFragmentManager, "NewsLocSel")
        }

        // 받아온 변수값에 따라 지역 타이틀 변경
        Log.d(TAG, "newsLocList: ${newsLocList}")
        val totalLocTitle  = String.format(getString(R.string.toolbar_tv_loc_changed_text_ex))
        when (newsLocList.size) {
            0 -> binding.newsToolbarTvLocChangedText.text = totalLocTitle
            1 -> binding.newsToolbarTvLocChangedText.text = newsLocList[0]
            else -> {
                val manyLocTitle = String.format(getString(R.string.location_select_text_many_loc_title, newsLocList[0], newsLocList.size - 1))
                binding.newsToolbarTvLocChangedText.text = manyLocTitle
            }
        }

    } //end onViewCreate

    private fun executeService() {
        // News API 호출
        showLoadingDialog(context!!)
        NewsService(this).tryGetRestaurants(page = 0, limit = 100,
                locationfilter_sungbuk = newsLocationFilter_sungBuk, locationfilter_suyu = newsLocationFilter_suYu, locationfilter_nowon = newsLocationFilter_noWon,
                expressionfilter_delicious = expression_great, expressionfilter_good = expression_good, expressionfilter_bad = expression_bad)
    }

    fun frameViewBind() {
        // expressionList 값에 따라 초기 버튼 상태 설정
        for (ex in expressionList) {
            Log.d(TAG, "ex: $ex")
            when (ex) {
                "great" -> {
                    binding.newTextGreat.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
                    binding.newImgGreat.setImageResource(R.drawable.great)
                    binding.newLayoutGreat.setBackgroundResource(R.drawable.news_sort_select_text_border)
                    ApplicationClass.isGreat = true
                    expressionCount += 1
                }
                "good" -> {
                    binding.newTextGood.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
                    binding.newImgGood.setImageResource(R.drawable.good)
                    binding.newLayoutGood.setBackgroundResource(R.drawable.news_sort_select_text_border)
                    ApplicationClass.isGood = true
                    expressionCount += 1
                }
                "bad" -> {
                    binding.newTextBad.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
                    binding.newImgBad.setImageResource(R.drawable.bad)
                    binding.newLayoutBad.setBackgroundResource(R.drawable.news_sort_select_text_border)
                    ApplicationClass.isBad = true
                    expressionCount += 1
                }
            }
        }
        // 리스트가 비어있으면 맛있다 필터 하나만 설정
        if (expressionList.isEmpty()){
            binding.newTextGreat.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
            binding.newImgGreat.setImageResource(R.drawable.great)
            binding.newLayoutGreat.setBackgroundResource(R.drawable.news_sort_select_text_border)
            expressionList.add("great")
            ApplicationClass.isGreat = true

            binding.newTextGood.setTextColor(ContextCompat.getColor(context!!, R.color.uncliked_color))
            binding.newImgGood.setImageResource(R.drawable.good_non)
            binding.newLayoutGood.setBackgroundResource(R.drawable.news_sort_select_text_unclicked_border)
            ApplicationClass.isGood = false

            binding.newTextBad.setTextColor(ContextCompat.getColor(context!!, R.color.uncliked_color))
            binding.newImgBad.setImageResource(R.drawable.bad_non)
            binding.newLayoutBad.setBackgroundResource(R.drawable.news_sort_select_text_unclicked_border)
            ApplicationClass.isBad = false
            expressionCount = 1
        }

        // 맛있다 필터
        binding.newLayoutGreat.setOnClickListener {
            Log.d(TAG, "isGreat = ${ApplicationClass.isGreat}")
            when (!ApplicationClass.isGreat) {
                true -> {
                    ApplicationClass.isGreat = !ApplicationClass.isGreat
                    expressionCount += 1
                    ApplicationClass.fGreat = 1
                    expressionList.add("great")
                    binding.newTextGreat.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
                    binding.newImgGreat.setImageResource(R.drawable.great)
                    binding.newLayoutGreat.setBackgroundResource(R.drawable.news_sort_select_text_border)
                    expression_great = 2
                    executeService()
                }
                false -> {
                    if (expressionCount <= 1) {
                        showCustomToast("모두 해제 할 수 없어요")
                    } else {
                        ApplicationClass.isGreat = !ApplicationClass.isGreat
                        expressionCount -= 1
                        ApplicationClass.fGreat = 0
                        expressionList.remove("great")
                        binding.newTextGreat.setTextColor(ContextCompat.getColor(context!!, R.color.uncliked_color))
                        binding.newImgGreat.setImageResource(R.drawable.great_non)
                        binding.newLayoutGreat.setBackgroundResource(R.drawable.news_sort_select_text_unclicked_border)
                        expression_great = 0
                        executeService()
                    }
                }
            }
            SharedPreferenced.putArrayStringItem(ApplicationClass.NEWS_EXPRESSION_LIST, expressionList)
            Log.d(TAG, "expressionCount: $expressionCount")
        }
        // 괜찮다 필터
        binding.newLayoutGood.setOnClickListener {
            Log.d(TAG, "isGood = ${ApplicationClass.isGood}")
            when (!ApplicationClass.isGood) {
                true -> {
                    ApplicationClass.isGood = !ApplicationClass.isGood
                    expressionCount += 1
                    ApplicationClass.fGood = 1
                    expressionList.add("good")
                    binding.newTextGood.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
                    binding.newImgGood.setImageResource(R.drawable.good)
                    binding.newLayoutGood.setBackgroundResource(R.drawable.news_sort_select_text_border)
                    expression_good = 1
                    executeService()
                }
                false -> {
                    if (expressionCount <= 1) {
                        showCustomToast("모두 해제 할 수 없어요")
                    } else {
                        ApplicationClass.isGood = !ApplicationClass.isGood
                        expressionCount -= 1
                        ApplicationClass.fGood = 0
                        expressionList.remove("good")
                        binding.newTextGood.setTextColor(ContextCompat.getColor(context!!, R.color.uncliked_color))
                        binding.newImgGood.setImageResource(R.drawable.good_non)
                        binding.newLayoutGood.setBackgroundResource(R.drawable.news_sort_select_text_unclicked_border)
                        expression_good = 0
                        executeService()
                    }
                }
            }
            SharedPreferenced.putArrayStringItem(ApplicationClass.NEWS_EXPRESSION_LIST, expressionList)
            Log.d(TAG, "expressionCount: $expressionCount")
        }
        // 별로 필터
        binding.newLayoutBad.setOnClickListener {
            Log.d(TAG, "isBad = ${ApplicationClass.isBad}")
            when (!ApplicationClass.isBad) {
                true -> {
                    ApplicationClass.isBad = !ApplicationClass.isBad
                    expressionCount += 1
                    ApplicationClass.fBad = 1
                    expressionList.add("bad")
                    binding.newTextBad.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
                    binding.newImgBad.setImageResource(R.drawable.bad)
                    binding.newLayoutBad.setBackgroundResource(R.drawable.news_sort_select_text_border)
                    expression_bad = -1
                    executeService()
                }
                false -> {
                    ApplicationClass.isBad = !ApplicationClass.isBad
                    if (expressionCount <= 1) {
                        showCustomToast("모두 해제 할 수 없어요")
                    } else {
                        expressionCount -= 1
                        ApplicationClass.fBad = 0
                        expressionList.remove("bad")
                        binding.newTextBad.setTextColor(ContextCompat.getColor(context!!, R.color.uncliked_color))
                        binding.newImgBad.setImageResource(R.drawable.bad_non)
                        binding.newLayoutBad.setBackgroundResource(R.drawable.news_sort_select_text_unclicked_border)
                        expression_bad = -1
                        executeService()
                    }
                }
            }
            SharedPreferenced.putArrayStringItem(ApplicationClass.NEWS_EXPRESSION_LIST, expressionList)
            Log.d(TAG, "expressionCount: $expressionCount")
        }
    }

    private inner class NewsTabPagerAdapter(fragment: NewsFragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> TotalFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(totalListKey, totalArgList)
                    }
                }
                1 -> FollowingFragment()
                else -> HolicFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(holicListKey, holicArgList)
                    }
                }
            }
        }
    }

    // API 호출
    override fun onGetTotalReviewSuccess(response: NewsResponse, reviewList: ArrayList<TotalReviewResultData>) {
        dismissLoadingDialog()
        Log.d(TAG, "onGetTotalReviewSuccess: $reviewList")

        totalArgList = reviewList

        holicArgList.clear()

        for (i in 0 until reviewList.size) {
            if (reviewList[i].isHolic == 1) {
                holicArgList.add(reviewList[i])
            }
        }
        Log.d(TAG, "reviewList size: ${reviewList.size}")
        Log.d(TAG, "holicArgList: $holicArgList")
        // 탭 레이아웃 어댑터 장착
        binding.newsVp.adapter = NewsTabPagerAdapter(this)

        TabLayoutMediator(binding.newsTabLayout, binding.newsVp) {tab, position ->
            when (position) {
                0 -> {
                    tab.setText(R.string.total_tab_name)
                }
                1 -> {
                    tab.setText(R.string.following)
                }
                2 -> {
                    tab.setText(R.string.hollic_tab_name)
                }
            }
        }.attach()

    }

    override fun onGetTotalReviewFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")

    }

}