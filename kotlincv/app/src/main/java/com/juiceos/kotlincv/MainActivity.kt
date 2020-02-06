package com.juiceos.kotlincv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juiceos.kotlincv.db.entity.CVEntity
import com.juiceos.kotlincv.models.CVViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    // TODO: make getter..
    private var cvModel : CVViewModel? = null
    private lateinit var cvAdapter : CVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        updateCV(null);
        setUpData()

    }

    private fun setUpData() {

        val observer = Observer<CVEntity>{
                cv -> updateCV(cv)
        }

        cvModel = CVViewModel(application)
        cvModel?.getCVObservable()?.observe(this, observer)

        cvAdapter = CVAdapter()

    }

    private fun updateCV(cv: CVEntity?) {

        if(cv == null){

            this.mainActivityProgress.visibility = View.VISIBLE;
            this.mainActivitySectionsGrid.visibility = View.GONE;

        } else {

            this.mainActivityProgress.visibility = View.GONE;
            this.mainActivitySectionsGrid.visibility = View.VISIBLE;

            title = cv.title
            cvAdapter.cv = cv

            if(mainActivitySectionsGrid.adapter == null){

                val cvLayoutManager = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)

                mainActivitySectionsGrid.apply {

                    layoutManager = cvLayoutManager
                    adapter = cvAdapter

                }

            } else
                cvAdapter.notifyDataSetChanged()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}
