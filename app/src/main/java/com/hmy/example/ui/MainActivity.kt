package com.hmy.example.ui

import android.content.Intent
import android.os.Bundle
import com.hmy.example.R
import com.hmy.example.api.ApiManager
import com.hmy.example.bean.ApiInfo
import com.hmy.example.db.DbHelper
import com.hmy.example.ui.base.BaseActivity
import com.hmy.example.util.LogUtil
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //设置历史记录的跳转事件
        main_history.setOnClickListener {
            startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
        }

        //设置下拉刷新
        main_refresh.setOnRefreshListener {
            loadLocalData()//加载本地最新数据
        }

        //自动刷新
        main_refresh.post {
            main_refresh.isRefreshing = true
            loadLocalData()//加载本地最新数据
        }

        startPolling()//异步开启轮询
    }

    /**
     * 加载本地数据
     */
    private fun loadLocalData() {
        DbHelper.getLastApiInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                main_refresh.isRefreshing = false
                if (it != null && it.size > 0 && it[0] != null) {
                    main_msg.text = it[0].toString()
                } else {
                    main_msg.text = getString(R.string.no_data)
                }
            }, Consumer {
                main_refresh.isRefreshing = false
                main_msg.text = it.message
                it.printStackTrace()
            })
    }

    private var disposable: Disposable? = null

    /**
     * 开始轮询
     */
    private fun startPolling() {
        disposable = Flowable.interval(5, 5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe(Consumer {
                //请求最新数据
                ApiManager.getApiJson()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer {
                        //存储最新数据
                        val newApiInfo = ApiInfo(it.toString(), System.currentTimeMillis())
                        DbHelper.saveApiInfo(newApiInfo)
                    }, Consumer {
                        val newApiInfo = ApiInfo(it.message, System.currentTimeMillis())
                        DbHelper.saveApiInfo(newApiInfo)
                        LogUtil.e(it.message)
                    })
            }, Consumer {
                LogUtil.e(it.message)
            })
    }


    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}