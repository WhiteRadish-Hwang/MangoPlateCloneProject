package com.example.mangoplate_mock_aos_radi.src.main.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mangoplate_mock_aos_radi.src.main.home.model.HomeRecyclerItems

class DiffUtilCallback(private val oldData: ArrayList<HomeRecyclerItems>, private val newData: ArrayList<HomeRecyclerItems> ) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldData[oldItemPosition].restaurantId
        val newItem = newData[newItemPosition].restaurantId

        return oldItem == newItem
    }

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldData[oldItemPosition] == newData[newItemPosition]
}