package com.example.mangoplate_mock_aos_radi.config

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.instance
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.sSharedPreferences

object SharedPreferenced {
    fun putSettingItem(key: String, value: String?) {
        Log.d(ApplicationClass.TAG, "Put $key (value : $value ) to ${ApplicationClass.MANGO_PLATE_APP}")
        val editor = ApplicationClass.sSharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getSettingItem(key: String): String? {
        Log.d(ApplicationClass.TAG, "Get $key from ${ApplicationClass.MANGO_PLATE_APP}")
        return instance.getSharedPreferences(ApplicationClass.MANGO_PLATE_APP, 0).getString(key, null)
    }
}