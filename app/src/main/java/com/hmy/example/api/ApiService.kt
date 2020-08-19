package com.hmy.example.api

import com.google.gson.JsonObject
import io.reactivex.Flowable
import retrofit2.http.GET

/**
 * 接口统一管理
 */
interface ApiService {
    /**
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    @GET(".")
    fun getApiList(): Flowable<JsonObject>
}