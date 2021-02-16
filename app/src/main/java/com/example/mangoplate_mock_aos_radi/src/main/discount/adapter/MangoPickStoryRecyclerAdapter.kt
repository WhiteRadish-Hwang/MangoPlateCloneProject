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
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.MangoPickStoryRecyclerItems

class MangoPickStoryRecyclerAdapter(val context: Context?, val itemList: ArrayList<MangoPickStoryRecyclerItems>): RecyclerView.Adapter<MangoPickStoryRecyclerAdapter.MangoPickStoryViewHolder>() {
    inner class MangoPickStoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mango_pick_story_img: ImageView = itemView.findViewById(R.id.mango_pick_story_recycler_img)
        val mango_pick_story_text_title: TextView = itemView.findViewById(R.id.mango_pick_story_text_title)
        val mango_pick_story_text_sub_title: TextView = itemView.findViewById(R.id.mango_pick_story_text_sub_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangoPickStoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_discount_mango_pick_story_recycler_items, parent, false)
        return MangoPickStoryViewHolder(view)
    }


    override fun onBindViewHolder(holder: MangoPickStoryViewHolder, position: Int) {
        val items = itemList[position]

        Glide.with(holder.mango_pick_story_img).load(items.image).placeholder(R.drawable.home_vp_img1).into(holder.mango_pick_story_img)
        holder.mango_pick_story_text_title.text = items.title
        holder.mango_pick_story_text_sub_title.text = items.subTitle
    }

    override fun getItemCount(): Int = itemList.size
}