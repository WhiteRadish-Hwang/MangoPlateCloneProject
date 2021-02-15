package com.example.mangoplate_mock_aos_radi.src.main.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.NEWS_EXPRESSION_LIST
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.fBad
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.fGood
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.fGreat
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isBad
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isGood
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isGreat
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.config.SharedPreferenced
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsFrameBinding
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.properties.Delegates

class NewsFrameFragment : BaseFragment<FragmentNewsFrameBinding>(FragmentNewsFrameBinding::bind, R.layout.fragment_news_frame){
    companion object {
        const val reviewListKey = "reviewListKey"
        var expressionList = ArrayList<String>()
    }

    var f_reviewList = ArrayList<TotalReviewResultData>()
    var expressionCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenced.getArrayStringItem(NEWS_EXPRESSION_LIST)?.let { expressionList = it }
        Log.d(TAG, "expressionList: $expressionList")
    }

    override fun onPause() {
        super.onPause()
        showCustomToast("onPause")
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newsVp.adapter = DiscountTabPagerAdapter(this)

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
        for (ex in expressionList) {
            Log.d(TAG, "ex: $ex")
            when (ex) {
                "great" -> {
                    binding.newTextGreat.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
                    binding.newImgGreat.setImageResource(R.drawable.great)
                    binding.newLayoutGreat.setBackgroundResource(R.drawable.news_sort_select_text_border)
                    isGreat = true
                    expressionCount += 1
                }
                "good" -> {
                    binding.newTextGood.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
                    binding.newImgGood.setImageResource(R.drawable.good)
                    binding.newLayoutGood.setBackgroundResource(R.drawable.news_sort_select_text_border)
                    isGood = true
                    expressionCount += 1
                }
                "bad" -> {
                    binding.newTextBad.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
                    binding.newImgBad.setImageResource(R.drawable.bad)
                    binding.newLayoutBad.setBackgroundResource(R.drawable.news_sort_select_text_border)
                    isBad = true
                    expressionCount += 1
                }
            }
        }
        if (expressionList.isEmpty()){
            binding.newTextGreat.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
            binding.newImgGreat.setImageResource(R.drawable.great)
            binding.newLayoutGreat.setBackgroundResource(R.drawable.news_sort_select_text_border)
            expressionList.add("great")
            isGreat = true

            binding.newTextGood.setTextColor(ContextCompat.getColor(context!!, R.color.uncliked_color))
            binding.newImgGood.setImageResource(R.drawable.good_non)
            binding.newLayoutGood.setBackgroundResource(R.drawable.news_sort_select_text_unclicked_border)
            isGood = false

            binding.newTextBad.setTextColor(ContextCompat.getColor(context!!, R.color.uncliked_color))
            binding.newImgBad.setImageResource(R.drawable.bad_non)
            binding.newLayoutBad.setBackgroundResource(R.drawable.news_sort_select_text_unclicked_border)
            isBad = false
            expressionCount = 1
        }



        binding.newLayoutGreat.setOnClickListener {
            Log.d(TAG, "isGreat = $isGreat")
            when (!isGreat) {
                true -> {
                    isGreat = !isGreat
                    expressionCount += 1
                    fGreat = 1
                    expressionList.add("great")
                    binding.newTextGreat.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
                    binding.newImgGreat.setImageResource(R.drawable.great)
                    binding.newLayoutGreat.setBackgroundResource(R.drawable.news_sort_select_text_border)
                }
                false -> {
                    if (expressionCount <= 1) {
                        showCustomToast("모두 해제 할 수 없어요")
                    } else {
                        isGreat = !isGreat
                        expressionCount -= 1
                        fGreat = 0
                        expressionList.remove("great")
                        binding.newTextGreat.setTextColor(ContextCompat.getColor(context!!, R.color.uncliked_color))
                        binding.newImgGreat.setImageResource(R.drawable.great_non)
                        binding.newLayoutGreat.setBackgroundResource(R.drawable.news_sort_select_text_unclicked_border)
                    }
                }
            }
            SharedPreferenced.putArrayStringItem(NEWS_EXPRESSION_LIST, expressionList)
            Log.d(TAG, "expressionCount: $expressionCount")
        }
        binding.newLayoutGood.setOnClickListener {
            Log.d(TAG, "isGood = $isGood")
            when (!isGood) {
                true -> {
                    isGood = !isGood
                    expressionCount += 1
                    fGood = 1
                    expressionList.add("good")
                    binding.newTextGood.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
                    binding.newImgGood.setImageResource(R.drawable.good)
                    binding.newLayoutGood.setBackgroundResource(R.drawable.news_sort_select_text_border)
                }
                false -> {
                    if (expressionCount <= 1) {
                        showCustomToast("모두 해제 할 수 없어요")
                    } else {
                        isGood = !isGood
                        expressionCount -= 1
                        fGood = 0
                        expressionList.remove("good")
                        binding.newTextGood.setTextColor(ContextCompat.getColor(context!!, R.color.uncliked_color))
                        binding.newImgGood.setImageResource(R.drawable.good_non)
                        binding.newLayoutGood.setBackgroundResource(R.drawable.news_sort_select_text_unclicked_border)
                    }
                }
            }
            SharedPreferenced.putArrayStringItem(NEWS_EXPRESSION_LIST, expressionList)
            Log.d(TAG, "expressionCount: $expressionCount")
        }
        binding.newLayoutBad.setOnClickListener {
            Log.d(TAG, "isBad = $isBad")
            when (!isBad) {
                true -> {
                    isBad = !isBad
                    expressionCount += 1
                    fBad = 1
                    expressionList.add("bad")
                    binding.newTextBad.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
                    binding.newImgBad.setImageResource(R.drawable.bad)
                    binding.newLayoutBad.setBackgroundResource(R.drawable.news_sort_select_text_border)
                }
                false -> {
                    isBad = !isBad
                    if (expressionCount <= 1) {
                        showCustomToast("모두 해제 할 수 없어요")
                    } else {
                        expressionCount -= 1
                        fBad = 0
                        expressionList.remove("bad")
                        binding.newTextBad.setTextColor(ContextCompat.getColor(context!!, R.color.uncliked_color))
                        binding.newImgBad.setImageResource(R.drawable.bad_non)
                        binding.newLayoutBad.setBackgroundResource(R.drawable.news_sort_select_text_unclicked_border)
                    }
                }
            }
            SharedPreferenced.putArrayStringItem(NEWS_EXPRESSION_LIST, expressionList)
            Log.d(TAG, "expressionCount: $expressionCount")
        }


    }

    private inner class DiscountTabPagerAdapter(fragment: NewsFrameFragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> TotalFragment()
                1 -> FollowingFragment()
                else -> HolicFragment()
            }
        }
    }
}