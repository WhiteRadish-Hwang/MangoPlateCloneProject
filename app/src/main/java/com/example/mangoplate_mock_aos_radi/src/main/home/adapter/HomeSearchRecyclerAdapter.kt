package com.example.mangoplate_mock_aos_radi.src.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.src.main.home.model.HomeSearchRecyclerItems

class HomeSearchRecyclerAdapter(val context: Context?, var itemList: ArrayList<HomeSearchRecyclerItems>): RecyclerView.Adapter<HomeSearchRecyclerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val homeSearchTextSearchWord: TextView = itemView.findViewById(R.id.home_search_text_search_word)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_search_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val items: HomeSearchRecyclerItems = itemList[position]
        holder.homeSearchTextSearchWord.text = items.word
    }

}