package com.example.homesensors.handlers

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


class SharePreferencesHandler(context: Context) : ISharePreferencesHandler {

    override fun saveToken(token: String): Boolean {
        val ed: SharedPreferences.Editor = tokenPreferences.edit()
        ed.putString(ARG_token, token)
        ed.apply()
        return true
    }

    override fun getToken(): String {
        return tokenPreferences.getString(ARG_token, "ooo"); }

    var context: Context
    var tokenPreferences: SharedPreferences

    private val ARG_SP_tokenPrefrences = "token_pref"
    private val ARG_token = "userToken"


    init {
        this.context = context
        tokenPreferences = context.getSharedPreferences(ARG_SP_tokenPrefrences, Activity.MODE_PRIVATE);
    }


}