package com.example.zhihuribao

import com.example.zhihuribao.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("news/latest")
    //别导错包，难受,返回值是observable
    fun getLatestMessage() : Observable<LatestMessage>

    @GET("news/before/{data}")
    //别导错包，难受,返回值是observable
    fun getOldMessage(@Path("data") data : Int) : Observable<OldMessage>

    @GET("story-extra/{id}}")
    //别导错包，难受,返回值是observable
    fun getExtraMessage(@Path("id") id : Int) : Observable<ExtraMessage>

    @GET("story/{id}/long-comments")
    //别导错包，难受,返回值是observable
    fun getLongComment(@Path("id") id : Int) : Observable<LongComment>

    @GET("story/{id}/short-comments")
    //别导错包，难受,返回值是observable
    fun getShortComment(@Path("id") id : Int) : Observable<ShortComment>
}