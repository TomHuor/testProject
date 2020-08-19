package com.hmy.example.ui

import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.hmy.example.R
import com.hmy.example.bean.ApiInfo
import com.hmy.example.db.DbHelper
import com.hmy.example.ui.adapter.HistoryAdapter
import com.hmy.example.ui.base.BaseActivity
import com.hmy.example.util.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_history.*


class HistoryActivity : BaseActivity() {
    private val PAGE_SIZE: Int = 20
    private var mPageIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        tool_bar?.apply {
            setSupportActionBar(tool_bar)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish() //返回
            }
        }

        //设置下拉刷新事件
        history_refresh.setOnRefreshListener {
            loadLocalHistoryData(0)
        }

        //设置上拉自动加载更多事件
        history_scroll.setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            if (v.getChildAt(0).height <= scrollY + v.height) {// 如果满足就是到底部了
                loadLocalHistoryData(++mPageIndex)//加载更多数据
            }
        }

        history_refresh.post {
            history_refresh.isRefreshing = true
            loadLocalHistoryData(0)
        }
    }

    /**
     * 加载本地历史数据
     */
    private fun loadLocalHistoryData(pageIndex: Int) {
        this.mPageIndex = pageIndex
        LogUtil.e("mPageIndex:$mPageIndex")
        DbHelper.getHistoryApiList(pageIndex, PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                if (pageIndex == 0) {
                    history_refresh.isRefreshing = false//停止动画
                    updateAdapter(it, true)
                } else {
                    updateAdapter(it, false)
                }

            }, Consumer {
                Toast.makeText(this@HistoryActivity, it.message, Toast.LENGTH_SHORT).show()
                it.printStackTrace()
                if (pageIndex == 0) history_refresh.isRefreshing = false//停止动画
            })
    }

    private var mAdapter: HistoryAdapter? = null

    private fun updateAdapter(data: MutableList<ApiInfo>?, isRefresh: Boolean) {
        if (data == null || data.size == 0) {
            Toast.makeText(this@HistoryActivity, getString(R.string.no_data), Toast.LENGTH_SHORT)
                .show()
            return
        }
        when {
            mAdapter == null -> {
                mAdapter = HistoryAdapter(data)//初始化
                initRecyclerView()
                history_recycler.adapter = mAdapter
            }
            isRefresh -> {
                mAdapter?.setDiffNewData(data) //刷新
            }
            else -> {
                mAdapter?.addData(data)//加载更多
            }
        }
    }

    private fun initRecyclerView() {
        //设置LayoutManager
        history_recycler.layoutManager = object : LinearLayoutManager(this, VERTICAL, false) {
            override fun canScrollVertically(): Boolean {
                return false //false:惯性滑动，反之。
            }
        }
        //设置分割器
        history_recycler.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        //设置动画
        history_recycler.itemAnimator = DefaultItemAnimator()
    }
}