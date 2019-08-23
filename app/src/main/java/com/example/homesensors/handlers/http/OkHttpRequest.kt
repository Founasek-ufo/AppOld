package com.example.homesensors.handlers.http

import android.util.Log
import okhttp3.*

class OkHttpRequest(client: OkHttpClient) {

    private var TAG = "OkHttpRequest"

    internal var client = OkHttpClient()

    companion object{
        val JSON = MediaType.parse("application/json; charset=utf-8")
    }

    init {
        this.client = client
    }


    fun post(url: String, parameters: HashMap<String, String>, callback: Callback): Call {
        val builder = FormBody.Builder()
        val it = parameters.entries.iterator()

        while (it.hasNext()){
            val pair = it.next() as Map.Entry<*,*>
            builder.add(pair.key.toString(), pair.value.toString())
        }

        val formBody = builder.build()
        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun postWithToken(url: String, parameters: HashMap<String, String>,token: String ,callback: Callback): Call {
        val builder = FormBody.Builder()
        val it = parameters.entries.iterator()

        while (it.hasNext()){
            val pair = it.next() as Map.Entry<*,*>
            builder.add(pair.key.toString(), pair.value.toString())
        }

        val formBody = builder.build()
        val request = Request.Builder()
            .addHeader("Content-Length","83")
           // .addHeader("application/json","charset=utf-8")
            .addHeader("Authorization", "Bearer "+token)
//            .addHeader("Cookie","id_token=" + token)
//            .addHeader("Cookie","id_token=" + token)
            .url(url)
            .post(formBody)
            .build()

        Log.d(TAG, "postWithToken: " +request.headers().toString() )

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }
}