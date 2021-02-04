package com.example.mangoplate_mock_aos_radi.src.main.news

import android.os.Bundle
import android.view.View
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsBinding

class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::bind, R.layout.fragment_news){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}