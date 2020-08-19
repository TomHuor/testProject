package com.hmy.example.api

import android.util.Log
import com.google.gson.JsonObject
import io.reactivex.Flowable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 接口调用管理
 */
object ApiManager {

    /**
     * 初始化okhttpclient
     */
    private val client: OkHttpClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> //打印retrofit日志
                Log.i("RetrofitLog", "retrofitBack = $message")
            }).apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    /**
     * 初始化retrofit
     */
    private val request: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        Retrofit.Builder()
            .client(client)
            .baseUrl(" https://api.github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    /**
     * 获取api数据
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getApiData(): Flowable<JsonObject> {
        return request.getApiList()
    }
}