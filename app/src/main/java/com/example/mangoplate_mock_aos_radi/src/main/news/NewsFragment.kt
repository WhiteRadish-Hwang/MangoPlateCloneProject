package com.example.mangoplate_mock_aos_radi.src.main.news

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.NEWS_LOC_LIST
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isGetNewsReviewItem
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.config.SharedPreferenced
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.home.location.GangbukFragment
import com.example.mangoplate_mock_aos_radi.src.main.home.location.LocationSelectFragment
import com.example.mangoplate_mock_aos_radi.src.main.news.NewsFrameFragment.Companion.reviewListKey
import com.example.mangoplate_mock_aos_radi.src.main.news.location.NewsGangbukFragment.Companion.newsLocList
import com.example.mangoplate_mock_aos_radi.src.main.news.location.NewsLocationSelectFragment
import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData
import kotlin.properties.Delegates

class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::bind, R.layout.fragment_news){
    var newsLocationFilter_sungBuk: Int = 0
    var newsLocationFilter_suYu: Int = 0
    var newsLocationFilter_noWon: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenced.getArrayStringItem(NEWS_LOC_LIST).let { newsLocList = it!! }
        Log.d(TAG, "newsLocList: ${newsLocList}")

        for (loc in GangbukFragment.locList) {
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

//        locList.clear()
//        SharedPreferenced.putArrayStringItem(LOC_LIST, locList)

        if (GangbukFragment.locList.isEmpty()) {
            newsLocationFilter_sungBuk = 1
            newsLocationFilter_suYu = 2
            newsLocationFilter_noWon = 3
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fmbt = (activity as MainActivity).supportFragmentManager.beginTransaction()
        fmbt.replace(R.id.news_layout_frame, NewsFrameFragment()).commit()


        binding.newsToolbarTvLocChangedText.setOnClickListener {
            val newsLocSelectFragment = NewsLocationSelectFragment()
            newsLocSelectFragment.show((activity as MainActivity).supportFragmentManager, "NewsLocSel")
        }

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

    }

}