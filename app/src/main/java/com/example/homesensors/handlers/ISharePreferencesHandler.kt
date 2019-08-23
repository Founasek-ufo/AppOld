package com.example.homesensors.handlers

interface ISharePreferencesHandler {

    fun saveToken(token: String): Boolean

    fun getToken():String
}