package com.example.mangoplate_mock_aos_radi.src.main.news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.TopListRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerInnerImageItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerItems

class TotalRecyclerInnerImageAdapter(val context: Context?, val itemList: ArrayList<TotalRecyclerInnerImageItems>?): RecyclerView.Adapter<TotalRecyclerInnerImageAdapter.TotalInnerViewHolder>() {
    inner class TotalInnerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val total_img_recycler_inner_image: ImageView = itemView.findViewById(R.id.total_img_recycler_inner_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalInnerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_news_total_recycler_inner_item, parent, false)
        return TotalInnerViewHolder(view)
    }


    override fun onBindViewHolder(holder: TotalInnerViewHolder, position: Int) {
        val items = itemList?.get(position)

        Glide.with(holder.total_img_recycler_inner_image).load(items?.innerImage).placeholder(null).into(holder.total_img_recycler_inner_image)

    }

    override fun getItemCount(): Int {
        return if (!itemList.isNullOrEmpty()) itemList.size
        else 0
    }
}