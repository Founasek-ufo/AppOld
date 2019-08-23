package com.example.homesensors.handlers

import android.util.Log
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
    var client = OkHttpClient()
    var request = OkHttpRequest(client)


    override fun getCurrentUserUID(): String {
        return "ddd"
    }

    init {
        Log.d(TAG, "init")
        callAuth()

    }

    fun callAuth() {
        val url = "https://iot.otavanet.cz/login/user"
        val map: HashMap<String, String> = hashMapOf("username" to "test@kouba.cz", "password" to "iotheslo321")
        Log.d(TAG, "callAuth " + map.toString())
        request.post(url, map, object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                Log.d(TAG, "onResponse callAuth " + responseData.toString())

                run {
                    try {
                        val json = JSONObject(responseData)
                        this@AuthorisationHandler.show(json)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Log.d(TAG, "callAuth onResponse Err:" + e.message)
                    }
                }


            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "callAuth onFailure " + e.message)
            }
        })
    }

    private fun show(json: JSONObject) {
        Log.d(TAG, "callAuth show " + json.get("token"))
        callOneSensor(json)
    }

    private fun showData(json: JSONObject) {
        Log.d(TAG, "callOneSensor show " + json.get("data"))
    }

    fun callOneSensor(json: JSONObject) {
        val url = "https://iot.otavanet.cz/graphql"
        val dotaz = "{ device(id: \"c9ec2fbe-3af2-4d4e-80bd-830cc3d34543\") { id data } }"
        val map: HashMap<String, String> = hashMapOf("query" to dotaz)
        Log.d(TAG, "callOneSensor " + map.toString())

        request.postWithToken(url, map, json.getString("token"), object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val resposeData = response.body()?.string()
                Log.d(TAG, "onResponse callOneSensor" + response.toString())

                run {
                    try {
                        val jsonSen = JSONObject(resposeData)
                        this@AuthorisationHandler.showData(jsonSen)
                    } catch (e: JSONException) {
                        Log.d(TAG, "callOneSensor onResponse Err:" + e.message)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure " + e.message)
            }

        })

    }

}