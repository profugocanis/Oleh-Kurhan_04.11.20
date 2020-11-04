package com.ijk.data.api

import com.google.gson.GsonBuilder
import com.ijk.data.base.baseMembersUrl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RestApi @Inject constructor() {
    private val client = OkHttpClient().newBuilder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .build()

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private var retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())


    var membersApi: MembersApi = retrofitBuilder
        .baseUrl(baseMembersUrl)
        .build()
        .create(MembersApi::class.java)
}