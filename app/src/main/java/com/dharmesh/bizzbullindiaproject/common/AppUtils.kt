package com.dharmesh.bizzbullindiaproject.common

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

object AppUtils {
    @JvmStatic
    fun toast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun checkNetworkConnection(context: Context): Boolean {
        val connectivity = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val networkInfo = connectivity.activeNetworkInfo
            return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
        }
        return false
    }

    fun getRequestBody(objt: JSONObject): RequestBody {
        return objt.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    fun getErrorMsg(result: String?): String {
        var error = ""
        try {
            val jsonObject = JSONObject(result)
            if (jsonObject.has("data")) {
                val jsonArray = JSONArray(jsonObject.getString("data"))
                for (i in 0 until jsonArray.length()) {
                    error += """
                        ${jsonArray[i]}
                        
                        """.trimIndent()
                }
            } else {
                error = jsonObject.getString("message")
            }
        } catch (ex: Exception) {
            ex.message
        }
        return error
    }
}