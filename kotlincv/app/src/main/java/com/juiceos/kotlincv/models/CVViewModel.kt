package com.juiceos.kotlincv.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.juiceos.kotlincv.db.entity.CVEntity
import com.juiceos.kotlincv.services.CVService

class CVViewModel(application: Application) : AndroidViewModel(application){

    private var cv : LiveData<CVEntity>? = null
    private var cvService: CVService? = null

    fun getCVObservable() : LiveData<CVEntity> {

        if(cv == null){
            cvService = CVService()
            cv = cvService?.getLiveCV()
        }

        return cv as LiveData<CVEntity>

    }

    fun dispose() {
        cvService?.dispose()
    }

    fun filter(query: String) {

        cvService?.filter(query)

    }

}