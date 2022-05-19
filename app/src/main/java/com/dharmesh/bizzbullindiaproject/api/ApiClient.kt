package com.dharmesh.bizzbullindiaproject.api

import android.content.Context
import com.dharmesh.bizzbullindiaproject.BuildConfig
import com.dharmesh.bizzbullindiaproject.common.Constants
import com.dharmesh.bizzbullindiaproject.common.Utility
import com.dharmesh.bizzbullindiaproject.common.Utility.gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {

    companion object {
        val BASE_URL = "http://13.51.195.182:1105"
        private var okHttpClient: OkHttpClient? = null

        private val logger = HttpLoggingInterceptor {
            Utility.logD("API", it)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(Constants.timeOut, TimeUnit.SECONDS)
            .readTimeout(Constants.timeOut, TimeUnit.SECONDS)
            .writeTimeout(Constants.timeOut, TimeUnit.SECONDS)
            .callTimeout(Constants.timeOut, TimeUnit.SECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(logger)
                }
            }

        fun getOkHttpClient(context: Context): OkHttpClient? {
            if (okHttpClient == null) {
                okHttpClient = clientBuilder
                    /*.authenticator(RefreshTokenAuthenticator(context))
                    .addInterceptor(HeadersInterceptor(context))*/.build()
            }
            return okHttpClient
        }

        fun retrofitAuth(context: Context): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(context)!!)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        fun retrofitAuth(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(
                    OkHttpClient.Builder()
                        .apply { if (BuildConfig.DEBUG) addInterceptor(logger) }.build()
                )
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}