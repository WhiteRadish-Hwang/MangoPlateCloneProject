package com.example.mangoplate_mock_aos_radi.src.main.news

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isGetNewsReviewItem
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.news.NewsFrameFragment.Companion.reviewListKey
import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData
import kotlin.properties.Delegates

class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::bind, R.layout.fragment_news){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fmbt = (activity as MainActivity).supportFragmentManager.beginTransaction()
        fmbt.replace(R.id.news_layout_frame, NewsFrameFragment()).commit()

    }

}