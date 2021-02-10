package com.example.mangoplate_mock_aos_radi.src.main.home.detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeRestaurantDetailsBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.news.adapter.TotalRecyclerInnerImageAdapter
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerInnerImageItems

class HomeRestaurantDetailsFragment: BaseFragment<FragmentHomeRestaurantDetailsBinding>(FragmentHomeRestaurantDetailsBinding::bind, R.layout.fragment_home_restaurant_details) {
    val itemList = ArrayList<TotalRecyclerInnerImageItems>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailsImgBackArrow.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
        
        binding.detailsToolbar.inflateMenu(R.menu.menu_details_toolbar)
        binding.detailsToolbar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.menu_home_toolbar_search -> {
                    showCustomToast("Clicked Camara Item")
                    true
                }
                R.id.menu_home_toolbar_map -> {
                    showCustomToast("Clicked Share Item")
                    true
                }

                else -> false
            }
        }

        setRecyclerAdapter()

        val fmbt = (activity as MainActivity).supportFragmentManager.beginTransaction()
        fmbt.replace(R.id.details_layout_frame, HomeRestaurantDetailsFrameFragment()).commit()
    }

    fun setRecyclerAdapter(){
        initData()
        binding.detailsRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = TotalRecyclerInnerImageAdapter(context, itemList)
        }
    }

    private fun initData() {
        val innerItem1 = TotalRecyclerInnerImageItems(innerImage = "https://images.unsplash.com/photo-1476718406336-bb5a9690ee2a?ixid=MXwxMjA3fDB8MHxzZWFyY2h8NXx8Zm9vZHxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60")
        val innerItem2 = TotalRecyclerInnerImageItems(innerImage = "https://images.unsplash.com/photo-1467003909585-2f8a72700288?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Nnx8Zm9vZHxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60")
        val innerItem3 = TotalRecyclerInnerImageItems(innerImage = "https://images.unsplash.com/photo-1473093295043-cdd812d0e601?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTB8fGZvb2R8ZW58MHx8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60")
        val innerItem4 = TotalRecyclerInnerImageItems(innerImage = "https://images.unsplash.com/photo-1481931098730-318b6f776db0?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTR8fGZvb2R8ZW58MHx8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60")
        val innerItem5 = TotalRecyclerInnerImageItems(innerImage = "https://images.unsplash.com/photo-1484723091739-30a097e8f929?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTh8fGZvb2R8ZW58MHx8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60")
        val items = arrayListOf<TotalRecyclerInnerImageItems>(innerItem1, innerItem2, innerItem3, innerItem4, innerItem5)
        for (i in items) {
            itemList.add(i)
        }
    }

}