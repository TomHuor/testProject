package com.hmy.example

import android.app.Application
import com.hmy.example.db.DbHelper

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DbHelper.init(this)
    }
}