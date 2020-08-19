package com.hmy.example.db

import android.content.Context
import com.hmy.example.bean.ApiInfo
import com.hmy.example.db.greendao.ApiInfoDao
import com.hmy.example.db.greendao.DaoMaster
import com.hmy.example.db.greendao.DaoMaster.DevOpenHelper
import com.hmy.example.db.greendao.DaoSession
import com.hmy.example.util.LogUtil
import io.reactivex.Observable


object DbHelper {
    lateinit var daoSession: DaoSession
    fun init(context: Context) {
        val devOpenHelper = DevOpenHelper(context, "mydb.db", null)
        val daoMaster =
            DaoMaster(devOpenHelper.writableDb)
        daoSession = daoMaster.newSession()
    }

    /**
     * 获取最后一条记录
     */
    fun getLastApiInfo(): Observable<MutableList<ApiInfo>> {
        return Observable.create { emitter ->
            try {
                val result: MutableList<ApiInfo> = daoSession.apiInfoDao.queryBuilder()
                    .orderDesc(ApiInfoDao.Properties.Time)
                    .limit(1)
                    .list()
                LogUtil.d("size:" + result.size)
                emitter.onNext(result)
            } catch (e: Exception) {
                emitter.onError(e)
            } finally {
                emitter.onComplete()
            }
        }
    }

    /**
     * 存储新的数据
     */
    fun saveApiInfo(data: ApiInfo) {
        try {
            LogUtil.d(data.toString())
            daoSession.insert(data)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getHistoryApiList(pageIndex: Int, pageSize: Int): Observable<MutableList<ApiInfo>> {
        return Observable.create { emitter ->
            try {
                val result: MutableList<ApiInfo> = daoSession.apiInfoDao.queryBuilder()
                    .orderDesc(ApiInfoDao.Properties.Time)
                    .offset(pageIndex * pageSize)
                    .limit(pageSize)
                    .list()

                LogUtil.d("result:$result")
                emitter.onNext(result)
            } catch (e: Exception) {
                emitter.onError(e)
            } finally {
                emitter.onComplete()
            }
        }
    }
}
