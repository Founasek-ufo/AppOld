package com.example.homesensors.`interface`

interface onAuthorizationListener {
    fun onAuthStart()

    fun onAutEnd(state: Boolean)

    fun getToken(toke: String)

}