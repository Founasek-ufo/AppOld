package com.example.homesensors.handlers

import android.app.Application
import android.util.Log
import com.example.homesensors.handlers.http.OkHttpRequest
import okhttp3.OkHttpClient

class DatabaseHandler(application: Application) {

    val TAG = "DatabaseHandler"
    var client = OkHttpClient()
    var request = OkHttpRequest(client)
    val url = "https://iot.otavanet.cz/graphql"
    val token: String

    var application: Application

    var sphHandler: SharePreferencesHandler


    init {
        Log.d(TAG, "init database handler")

        this.application = application
        sphHandler = SharePreferencesHandler(application.applicationContext)

        Log.d(TAG, sphHandler.getToken())

        token = sphHandler.getToken()
    }

}