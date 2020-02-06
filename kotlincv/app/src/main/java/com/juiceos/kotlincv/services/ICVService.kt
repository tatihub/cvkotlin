package com.juiceos.kotlincv.services

import com.juiceos.kotlincv.db.entity.CVEntity
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ICVService{

    @GET("a90ea35867895249243dc203e40050ba/raw/615fda0639a623f3ffc78950998f368edb78718f/cvkotlin.json")
    fun getCV() : Observable<CVEntity>

    companion object {

        fun create(): ICVService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://gist.githubusercontent.com/tatihub/")
                .build()

            return retrofit.create(ICVService::class.java)
        }

    }

}