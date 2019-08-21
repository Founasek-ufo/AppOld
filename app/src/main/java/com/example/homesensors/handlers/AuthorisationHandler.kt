package com.example.homesensors.handlers

import android.util.Log

class AuthorisationHandler : IAuthorisationHandler {

    private val TAG = "AuthorisationHandler"

    override fun getCurrentUserUID(): String {
        return "ddd"
    }

    init {
        Log.d(TAG, "init")
        callAuth()
    }

    fun callAuth() {


    }
}