package com.example.mangoplate_mock_aos_radi.src.main.discount.adapter

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

class TopListRecyclerAdapter(val context: Context?, val itemList: ArrayList<TopListRecyclerItems>): RecyclerView.Adapter<TopListRecyclerAdapter.TopListViewHolder>() {
    inner class TopListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val top_list_img: ImageView = itemView.findViewById(R.id.top_list_img)
        val top_list_text_title: TextView = itemView.findViewById(R.id.top_list_text_title)
        val top_list_text_sub_title: TextView = itemView.findViewById(R.id.top_list_text_sub_title)
        val top_list_text_view_count: TextView = itemView.findViewById(R.id.top_list_text_view_count)
        val top_list_text_upload_date_ago: TextView = itemView.findViewById(R.id.top_list_text_upload_date_ago)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_discount_top_list_recycler_items, parent, false)
        return TopListViewHolder(view)
    }


    override fun onBindViewHolder(holder: TopListViewHolder, position: Int) {
        val items = itemList[position]

        Glide.with(holder.top_list_img).load(items.image).placeholder(R.drawable.home_vp_img1).into(holder.top_list_img)
        holder.top_list_text_title.text = items.title
        holder.top_list_text_sub_title.text = items.subTitle
        holder.top_list_text_upload_date_ago.text = items.uplosdDateAgo
        holder.top_list_text_view_count.text = items.viewCount

    }

    override fun getItemCount(): Int = itemList.size
}