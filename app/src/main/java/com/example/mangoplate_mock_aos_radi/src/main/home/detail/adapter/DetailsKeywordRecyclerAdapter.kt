package com.example.mangoplate_mock_aos_radi.src.main.home.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.src.main.home.detail.model.DetailsKeywordRecyclerItems

class DetailsKeywordRecyclerAdapter(val context: Context?, var itemList: ArrayList<DetailsKeywordRecyclerItems>): RecyclerView.Adapter<DetailsKeywordRecyclerAdapter.ItemViewHolder>() {
    interface MyKeywordItemClickListener {
        fun onItemClick(position: Int)
    }
    private lateinit var mItemClickListener: MyKeywordItemClickListener

    fun setMyKeywordItemClickListener(itemClickListener: MyKeywordItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val keyword: TextView = itemView.findViewById(R.id.details_text_keyword)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_details_keyword_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val items: DetailsKeywordRecyclerItems = itemList[position]

        holder.keyword.text = items.keyword

    }

    fun clearItemList() {
        itemList.clear()
        notifyDataSetChanged()
    }

}