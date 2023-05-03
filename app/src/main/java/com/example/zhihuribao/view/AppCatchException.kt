package com.example.zhihuribao.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

open class AppCatchException : AppCompatActivity() {
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        Handler(Looper.myLooper()!!).post {
            while (true){
                try {
                    Looper.loop()
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}