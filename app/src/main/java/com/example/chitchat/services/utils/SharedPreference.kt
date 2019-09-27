package com.example.chitchat.services.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context)
{

    val myPriference:String = "myPref"
    val sharedPreferences : SharedPreferences? = context!!.getSharedPreferences(myPriference,Context.MODE_PRIVATE)


    val editor : SharedPreferences.Editor = sharedPreferences!!.edit()

    fun addString(key: String,value:String)
    {
        editor.putString(key,value)
        editor.commit()
    }

    fun getString(key:String):String
    {
        return sharedPreferences!!.getString(key,"")!!
    }


    fun getLoginSession():Boolean
    {
        return sharedPreferences!!.getBoolean("ID",false)
    }

    fun setLoginSession(number: String)
    {
        editor.putBoolean("ID",true)
        editor.commit()
    }

    fun clearSharedPreference()
    {
        editor.clear()
        editor.commit()
    }
}

