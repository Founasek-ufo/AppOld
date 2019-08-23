package com.example.homesensors.handlers

import android.util.Log
import com.example.homesensors.handlers.http.OkHttpRequest
import okhttp3.OkHttpClient

class DatabaseHandler {

    private val TAG = "DatabaseHandler"
    private var client = OkHttpClient()
    private var request = OkHttpRequest(client)
    val url = "https://iot.otavanet.cz/graphql"
    
    init {
        Log.d(TAG, "init authorization")
    }


//    private fun showData(json: JSONObject) {
//        Log.d(TAG, "callOneSensor show " + json.get("data"))
//    }
//
//    fun callOneSensor(json: JSONObject) {
//        val url = "https://iot.otavanet.cz/graphql"
//        val dotaz = "{ device(id: \"c9ec2fbe-3af2-4d4e-80bd-830cc3d34543\") { id data } }"
//        val map: HashMap<String, String> = hashMapOf("query" to dotaz)
//        Log.d(TAG, "callOneSensor " + map.toString())
//
//        request.postWithToken(url, map, json.getString("token"), object : Callback {
//            override fun onResponse(call: Call, response: Response) {
//                val resposeData = response.body()?.string()
//                Log.d(TAG, "onResponse callOneSensor" + response.toString())
//
//                run {
//                    try {
//                        val jsonSen = JSONObject(resposeData)
//                        this@AuthorisationHandler.showData(jsonSen)
//                    } catch (e: JSONException) {
//                        Log.d(TAG, "callOneSensor onResponse Err:" + e.message)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d(TAG, "onFailure " + e.message)
//            }
//
//        })
//
//    }
}