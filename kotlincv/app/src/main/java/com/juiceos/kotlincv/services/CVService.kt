package com.juiceos.kotlincv.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juiceos.kotlincv.db.entity.CVEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

// TODO: change this to singleton
class CVService {

    private lateinit var cvData:MutableLiveData<CVEntity>

    private val cvService by lazy {
        ICVService.create()
    }

    var disposable: Disposable? = null

    fun getLiveCV(): LiveData<CVEntity> {

        Log.i("CVSvc", "Getting live CV")

        cvData = MutableLiveData()

        downloadCV()

        return cvData

    }

    private fun downloadCV() {

        disposable =
            cvService.getCV()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({cv -> updateCV(cv)}, {error -> showError(error)})

        Log.i("CVSvc", "Download CV")

    }

    private fun showError(error: Throwable?) {

        Log.e("CVSvc", String.format("Error loading cv %s", error?.message))

        val errorCv = CVEntity()
        errorCv.error = 1
        errorCv.errorMessage = "Unable to download cv"

        cvData.value = errorCv

    }

    private fun updateCV(cv: CVEntity?) {

        Log.i("CVSvc", String.format("Cv title: %s", cv?.title))

        cvData.value = cv

    }

    fun dispose(){
        disposable?.dispose()
        disposable = null
    }

}