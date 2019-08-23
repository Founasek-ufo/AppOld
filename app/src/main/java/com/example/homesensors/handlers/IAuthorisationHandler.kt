package com.example.homesensors.handlers

import com.example.homesensors.`interface`.onAuthorizationListener

interface IAuthorisationHandler {

    fun callAuthorization(name: String, password: String,listner: onAuthorizationListener): Boolean
}