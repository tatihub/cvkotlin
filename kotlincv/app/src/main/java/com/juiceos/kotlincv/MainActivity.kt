package com.juiceos.kotlincv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.juiceos.kotlincv.db.entity.CVEntity
import com.juiceos.kotlincv.models.CVViewModel
import com.juiceos.kotlincv.services.CVService

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    // TODO: make getter..
    private var cvModel : CVViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        updateCV(null);
        setUpLiveData()

    }

    private fun setUpLiveData() {

        val observer = Observer<CVEntity>{
                cv -> updateCV(cv)
        }

        cvModel = CVViewModel(application)
        cvModel?.getCVObservable()?.observe(this, observer)

    }

    private fun updateCV(cv: CVEntity?) {

        if(cv == null){

            this.mainActivityProgress.visibility = View.VISIBLE;
            this.mainActivitySectionsGrid.visibility = View.GONE;

        } else {

            this.mainActivityProgress.visibility = View.GONE;
            this.mainActivitySectionsGrid.visibility = View.VISIBLE;

            title = cv.title

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
