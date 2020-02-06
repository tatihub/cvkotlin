package com.juiceos.kotlincv.services

import android.database.Observable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juiceos.kotlincv.db.entity.CVEntity
import com.juiceos.kotlincv.db.entity.CVSectionEntity
import com.juiceos.kotlincv.models.CVViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET

// TODO: change this to singleton
class CVService {

    private lateinit var cvData:MutableLiveData<CVEntity>

    val cvService by lazy {
        ICVService.create()
    }

    var disposable: Disposable? = null

    fun getLiveCV(): LiveData<CVEntity> {

        Log.i("CVSvc", "Getting live CV")

        cvData = MutableLiveData()

        /*
        val cv = CVEntity("tasCv", "Mr. Tati CV")

        val sec1 = CVSectionEntity("July 5, 2015", "Acme Inc.")
        val sec2 = CVSectionEntity("Jan 10, 2010", "ABCDE Corp.")

        sec1.details = "Lead a team of soldiers to the capture of mount zion in Egypt"
        sec2.details = "Negotiated a peace deal between Israelis and Palestinians. Brought peace to the middle east"

        cv.sections.add(sec1)
        cv.sections.add(sec2)

        data.value = cv
        */

        downloadCV()

        return cvData

    }

    private fun downloadCV() {

        Log.i("CVSvc", "Download CV")

        disposable =
            cvService.getCV()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({cv -> updateCV(cv)}, {error -> showError(error)})

    }

    private fun showError(error: Throwable?) {

        Log.e("CVSvc", String.format("Error loading cv %s", error?.message))

    }

    private fun updateCV(cv: CVEntity?) {

        Log.i("CVSvc", String.format("Cv title: %s", cv?.title))

        cvData.value = cv

    }

}