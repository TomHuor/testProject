package com.hmy.example.api

import android.util.Log
import com.google.gson.JsonObject
import com.hmy.example.bean.ApiInfo
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
    private val client: OkHttpClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> //打印retrofit日志
                Log.i("RetrofitLog", "retrofitBack = $message")
            }).apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

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
     * 签到
     * 重试机制：普通异常、初始化异常
     *
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getApiJson(): Flowable<JsonObject> {
        return request.getApiList()
    }
}