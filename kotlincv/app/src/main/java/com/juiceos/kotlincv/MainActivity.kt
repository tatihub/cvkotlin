package com.juiceos.kotlincv

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.juiceos.kotlincv.db.entity.CVEntity
import com.juiceos.kotlincv.models.CVViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cv_content_main.*

class MainActivity : AppCompatActivity() {

    private var cvModel : CVViewModel? = null
    private lateinit var cvAdapter : CVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // first show loading
        updateUI(null)

        // download cv & other data ops
        setUpData()

    }

    private fun setUpData() {

        cvAdapter = CVAdapter()

        val observer = Observer<CVEntity>{
                cv -> updateUI(cv)
        }

        cvModel = CVViewModel(application)
        cvModel?.getCVObservable()?.observe(this, observer)

    }

    override fun onDestroy() {
        super.onDestroy()
        cvModel?.dispose()
    }

    private fun updateUI(cv: CVEntity?) {

        if(cv == null){

            // we don't have cv, show loading and wait.
            this.mainActivityProgress.visibility = View.VISIBLE
            this.mainActivitySectionsGrid.visibility = View.GONE
            this.mainActivityCvError.visibility = View.GONE

        } else {

            // we have cv.
            this.mainActivityProgress.visibility = View.GONE
            this.mainActivitySectionsGrid.visibility = if(cv.error == 0){ View.VISIBLE } else View.GONE
            this.mainActivityCvError.visibility = if(cv.error > 0){ View.VISIBLE } else View.GONE

            this.mainActivityCvError.text = cv.errorMessage
            title = HtmlCompat.fromHtml(cv.title ?: getString(R.string.cv_untiled), HtmlCompat.FROM_HTML_MODE_LEGACY)

            if(cv.error == 0) {

                // and it's not invalid.

                val spanCount: Int = this.resources.getInteger(R.integer.cvGridSpanCount)

                cvAdapter.cv = cv

                if (mainActivitySectionsGrid.adapter != cvAdapter) {

                    Log.i("CVSvc", "Adding adapter")

                    val cvLayoutManager =
                        GridLayoutManager(this, spanCount, GridLayoutManager.VERTICAL, false)

                    mainActivitySectionsGrid.apply {

                        layoutManager = cvLayoutManager
                        adapter = cvAdapter

                    }

                } else {

                    Log.i("CVSvc", "Updating adapter")
                    cvAdapter.notifyDataSetChanged()

                }
            }

        }

    }

}
