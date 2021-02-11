package com.example.mangoplate_mock_aos_radi.config

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.instance
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.sSharedPreferences
import com.example.mangoplate_mock_aos_radi.src.main.discount.adapter.EatDealRecyclerAdapter

object SharedPreferenced {
    fun putSettingItem(key: String, value: String?) {
        Log.d(ApplicationClass.TAG, "Put $key (value : $value ) to ${ApplicationClass.MANGO_PLATE_APP}")
        val editor = sSharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getSettingItem(key: String): String? {
        Log.d(ApplicationClass.TAG, "Get $key from ${ApplicationClass.MANGO_PLATE_APP}")
        Log.d(ApplicationClass.TAG, "Return ${sSharedPreferences.getString(key, null)}")
        return sSharedPreferences.getString(key, null)
    }

}