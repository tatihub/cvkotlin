package com.juiceos.kotlincv.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juiceos.kotlincv.db.entity.CVEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class CVService {

    private var query: String? = null
    private lateinit var cvData:MutableLiveData<CVEntity>

    private val cvService by lazy {
        ICVService.create()
    }

    var disposable: Disposable? = null

    fun getLiveCV(): LiveData<CVEntity> {

        cvData = MutableLiveData()

        downloadCV()

        return cvData

    }

    private fun downloadCV() {

        disposable =
            cvService.getCV()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({cv -> updateCV(cv, query)}, {error -> showError(error)})

    }

    private fun showError(error: Throwable?) {

        if (error != null) {
            Log.e("CV",  error.message ?: "Unknown cv download error")
        }

        val errorCv = CVEntity()
        errorCv.error = 1
        errorCv.message = "Unable to download cv"

        cvData.value = errorCv

    }

    private fun updateCV(cv: CVEntity?, query: String?) {

        cvData.value = applyCVFilter(cv, query)

    }

    companion object{

        fun applyCVFilter(cv: CVEntity?, query: String?): CVEntity? {

            if(cv == null)
                return  null

            if(query != null && query.isNotEmpty()){

                for (cvIdx in cv.sections.size - 1 downTo 0){

                    if(!foundText(cv.sections[cvIdx].title, query))
                        if(!foundText(cv.sections[cvIdx].details, query))
                            cv.sections.removeAt(cvIdx)

                }

                cv.message = "Found ${cv.sections.size} results in filter"

            } else
                cv.message = null

            return  cv

        }

        fun foundText(hay: String?, needle: String?): Boolean {

            if(hay == null || hay.isEmpty())
                return false

            if(needle == null || needle.isEmpty())
                return  false

            return hay.toLowerCase(Locale.US).contains(needle.toLowerCase(Locale.US))

        }

    }

    fun dispose(){
        disposable?.dispose()
        disposable = null
    }

    fun filter(query: String) {

        this.query = query

        // download cv again...
        downloadCV()

    }

}