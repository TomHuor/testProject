package com.hmy.example

import com.hmy.example.db.DbHelper
import com.hmy.example.util.LogUtil
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DBUnitTest {

    @Test
    fun testGetLastApiInfo() {
        DbHelper.getLastApiInfo()
            .subscribeOn(Schedulers.io())
            .subscribe(Consumer {
                LogUtil.i(it)

                assertNotNull(it)
            }, Consumer {
                assert(false)
                LogUtil.e(it.message)
            })
    }

    @Test
    fun testGetApiInfoList() {
        DbHelper.getHistoryApiList(0, 20)
            .subscribeOn(Schedulers.io())
            .subscribe(Consumer {
                LogUtil.i(it)

                assertNotNull(it)
            }, Consumer {
                assert(false)
                LogUtil.e(it.message)
            })
    }
}