package com.example.mangoplate_mock_aos_radi.src.main.location.adapter

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
import com.example.mangoplate_mock_aos_radi.src.main.location.model.LocSelectRecyclerItems

class LocSelectRecyclerAdapter(val context: Context?, var itemList: ArrayList<LocSelectRecyclerItems>): RecyclerView.Adapter<LocSelectRecyclerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val locSelectTextSearchWord: TextView = itemView.findViewById(R.id.loc_select_text_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_loc_select_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val items: LocSelectRecyclerItems = itemList[position]
        holder.locSelectTextSearchWord.text = items.location
    }

}