package com.example.mangoplate_mock_aos_radi.config

import android.preference.PreferenceManager
import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.sSharedPreferences
import org.json.JSONArray
import org.json.JSONException


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

    fun putArrayStringItem(key: String, value: ArrayList<String>?) {
        val editor = sSharedPreferences.edit()

        val jsonList = JSONArray()
        if (value != null) {
            for (i in 0 until value.size) {
                jsonList.put(value[i])
            }
        }
        if (value != null) {
            if (value.isNotEmpty()) {
                editor.putString(key, jsonList.toString())
            } else {
                editor.putString(key, null)
            }
        }
        editor.apply()
        Log.d(ApplicationClass.TAG, "Put $key (value : $value ) to ${ApplicationClass.MANGO_PLATE_APP}")
        Log.d(ApplicationClass.TAG, "result $key (value : $jsonList ) to ${ApplicationClass.MANGO_PLATE_APP}")
    }

    fun getArrayStringItem(key: String): ArrayList<String>? {
        Log.d(ApplicationClass.TAG, "Get $key from ${ApplicationClass.MANGO_PLATE_APP}")
        val json = sSharedPreferences.getString(key, null)
        val returnList = ArrayList<String>()
        if (json != null) {
            try {
                val jsonList = JSONArray(json)
                for (i in 0 until jsonList.length()) {
                    val url = jsonList.optString(i)
                    returnList.add(url)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        Log.d(ApplicationClass.TAG, "Return $returnList")
        return returnList
    }
}