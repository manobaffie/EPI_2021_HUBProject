package com.example.terminal

import android.util.Log
import androidx.lifecycle.ViewModel
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

interface GetLastCallback {
    fun lastCallback(response: JSONObject?)
}

class NetworkClient : ViewModel() {
    private val client = OkHttpClient()

    fun requestToApi(params : String, getLastCallback: GetLastCallback) {

        val request = Request.Builder()
            .url(API + params)
            .build()
        Log.e("url ->", API + params)

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    getLastCallback.lastCallback(JSONObject(response.body!!.string()))
                }
            }
        })
    }

    fun postToApi(params: String, cmd : String, getLastCallback: GetLastCallback) {

        val body = FormBody.Builder()
            .add("cmd", cmd)
            .build()

        val request = Request.Builder()
            .url(API + params)
            .post(body)
            .build()

        Log.e("url ->", API + params)

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            getLastCallback.lastCallback(JSONObject(response.body!!.string()))
        }
    }

    fun setURL(ip : String, port : String) {
        API = "http://$ip:$port/"
        Log.e("->", API)

    }

    companion object {
        var API = ""
    }
}