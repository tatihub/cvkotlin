package com.juiceos.kotlincv.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juiceos.kotlincv.db.entity.CVEntity
import com.juiceos.kotlincv.models.CVViewModel

// TODO: change this to singleton
class CVService {
    fun getLiveCV(): LiveData<CVEntity> {

        val data = MutableLiveData<CVEntity>()

        val cv = CVEntity("tasCv", "Mr. Tati CV");
        data.value = cv;

        return data;

    }
}