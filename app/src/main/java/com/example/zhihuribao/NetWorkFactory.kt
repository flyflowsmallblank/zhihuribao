package com.example.zhihuribao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class NetWorkFactory (val baseUrl : String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val testViewModel = NetWork(baseUrl)
        return testViewModel as T
    }
}