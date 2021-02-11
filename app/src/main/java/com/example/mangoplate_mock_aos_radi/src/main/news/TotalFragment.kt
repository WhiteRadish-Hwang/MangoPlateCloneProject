package com.example.mangoplate_mock_aos_radi.src.main.news

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.fBad
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.fGood
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.fGreat
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsTotalBinding
import com.example.mangoplate_mock_aos_radi.src.main.news.adapter.HollicRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.news.adapter.TotalRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.news.model.HollicRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerInnerImageItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerItems

class TotalFragment : BaseFragment<FragmentNewsTotalBinding>(FragmentNewsTotalBinding::bind, R.layout.fragment_news_total){
    val itemList = ArrayList<TotalRecyclerItems>()
    val innerItemList = ArrayList<TotalRecyclerInnerImageItems>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerAdapter()
    }

    fun setRecyclerAdapter(){
        initData()
        innerInitData()
        binding.totalRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = TotalRecyclerAdapter(context, itemList)
        }
    }

    private fun innerInitData() {
        val innerItem1 = TotalRecyclerInnerImageItems(innerImage = "https://images.unsplash.com/photo-1476718406336-bb5a9690ee2a?ixid=MXwxMjA3fDB8MHxzZWFyY2h8NXx8Zm9vZHxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60")
        val innerItem2 = TotalRecyclerInnerImageItems(innerImage = "https://images.unsplash.com/photo-1467003909585-2f8a72700288?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Nnx8Zm9vZHxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60")
        val innerItem3 = TotalRecyclerInnerImageItems(innerImage = "https://images.unsplash.com/photo-1473093295043-cdd812d0e601?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTB8fGZvb2R8ZW58MHx8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60")
        val innerItem4 = TotalRecyclerInnerImageItems(innerImage = "https://images.unsplash.com/photo-1481931098730-318b6f776db0?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTR8fGZvb2R8ZW58MHx8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60")
        val innerItem5 = TotalRecyclerInnerImageItems(innerImage = "https://images.unsplash.com/photo-1484723091739-30a097e8f929?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTh8fGZvb2R8ZW58MHx8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60")
        val items = arrayListOf<TotalRecyclerInnerImageItems>(innerItem1, innerItem2, innerItem3, innerItem4, innerItem5)
        for (i in items) {
            innerItemList.add(i)
        }
    }

    private fun initData() {
        for (i in 0..5) {
            val item1 = TotalRecyclerItems(innerImageItems = innerItemList, reviewCount = 0, likeCount = 0, followerCount = 0, commentCount = 0, restaurantInfo = "@ 공화춘 - 경북 김천시",dateTimeAgo = "20시간 전",
                mainContentText = "놀라운 식사를 할 수 있게 서비스! 이 모든 것은 프티 우수한 에피타이저가 나오 네. 이 푸아그라 롤리팝, 레드 와인과 버섯, 랍스터 연예, 물론 치즈, 초콜릿 디저트 크레페 수세 및 모두 뛰어난, 필히 소금에 절인 브리오슈! 주방장은 우수한 그녀는 딸과 함께 걸어가실만할 의 너트 알레르기 와 완전히 다른 네 가지의 에피타이저가 나오고 있는 쁘띠 너트 및 하지 않았다. 미슐랭 스타 레스토랑에서 먹을 수 있는 여러 경험이 강력 추천합니다."
                        )
//            ,filter_great = fGreat, filter_good = fGood, filter_bad = fBad
            itemList.add(item1)
        }
    }

}