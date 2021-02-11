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
import com.example.mangoplate_mock_aos_radi.src.main.news.model.FollowingRecyclerItems

class FollowingRecyclerAdapter(val context: Context?, val itemList: ArrayList<FollowingRecyclerItems>): RecyclerView.Adapter<FollowingRecyclerAdapter.FollowingViewHolder>() {
    inner class FollowingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val top_list_img: ImageView = itemView.findViewById(R.id.top_list_img)
        val top_list_text_title: TextView = itemView.findViewById(R.id.top_list_text_title)
        val top_list_text_sub_title: TextView = itemView.findViewById(R.id.top_list_text_sub_title)
        val top_list_text_view_count: TextView = itemView.findViewById(R.id.top_list_text_view_count)
        val top_list_text_upload_date_ago: TextView = itemView.findViewById(R.id.top_list_text_upload_date_ago)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_discount_top_list_recycler_items, parent, false)
        return FollowingViewHolder(view)
    }


    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val items = itemList[position]

        Glide.with(holder.top_list_img).load(items.image).placeholder(R.drawable.home_vp_img1).into(holder.top_list_img)
        holder.top_list_text_title.text = items.title
        holder.top_list_text_sub_title.text = items.subTitle


    }

    override fun getItemCount(): Int = itemList.size
}