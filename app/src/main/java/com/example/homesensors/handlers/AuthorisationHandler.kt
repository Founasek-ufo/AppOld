package com.example.homesensors.handlers

import android.util.Log
import com.example.homesensors.`interface`.onAuthorizationListener
import com.example.homesensors.handlers.http.OkHttpRequest
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class AuthorisationHandler : IAuthorisationHandler {

    private val TAG = "AuthorisationHandler"
    private var client = OkHttpClient()
    private var request = OkHttpRequest(client)


    override fun callAuthorization(name: String, password: String, listner: onAuthorizationListener): Boolean {

        listner.onAuthStart()

        val url = "https://iot.otavanet.cz/login/user"
        val map: HashMap<String, String> = hashMapOf("username" to name.trim(), "password" to password.trim())

        request.post(url, map, object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                Log.d(TAG, "onResponse callAuth " + responseData.toString())

                var json: String
                try {
                    json = JSONObject(responseData).get("token").toString()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    json = "null"
                    Log.d(TAG, "callAuth onResponse Err:" + e.message)
                }

                listner.getToken(json)
                listner.onAutEnd(true)

            }

            override fun onFailure(call: Call, e: IOException) {
                listner.onAutEnd(false)
                Log.d(TAG, "callAuth onFailure " + e.message)
            }
        })

        return true
    }

    init {
        Log.d(TAG, "init authorization")
    }

}