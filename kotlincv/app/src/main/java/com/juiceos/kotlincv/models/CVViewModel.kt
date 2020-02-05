package com.juiceos.kotlincv.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juiceos.kotlincv.db.entity.CVEntity
import com.juiceos.kotlincv.services.CVService

class CVViewModel(application: Application) : AndroidViewModel(application){

    private var cv : LiveData<CVEntity>? = null

    fun getCVObservable() : LiveData<CVEntity> {

        if(cv == null){
            val cvService = CVService()
            cv = cvService.getLiveCV();
        }

        return cv as LiveData<CVEntity>;

    }

}