package com.hmy.example.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

    }
}