package com.juiceos.kotlincv

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.juiceos.kotlincv.db.entity.CVEntity
import com.juiceos.kotlincv.models.CVViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cv_content_main.*

class MainActivity : AppCompatActivity(), TextWatcher, View.OnClickListener,
    TextView.OnEditorActionListener {

    private var cvModel : CVViewModel? = null
    private lateinit var cvAdapter : CVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setupViews()

        // first show loading
        updateUI(null)

        // download cv & other data ops
        setUpData()

    }

    private fun setupViews() {

        mainActivityCvSearchText.addTextChangedListener(this)
        mainActivityCvSearchText.setOnEditorActionListener(this)
        mainActivityCvSearchButton.setOnClickListener(this)

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
            updateMessageText(getString(R.string.cv_loading))

        } else {

            // we have cv.
            this.mainActivityProgress.visibility = View.GONE
            this.mainActivitySectionsGrid.visibility = if(cv.error == 0){ View.VISIBLE } else View.GONE

            title = HtmlCompat.fromHtml(cv.title ?: getString(R.string.cv_untiled), HtmlCompat.FROM_HTML_MODE_LEGACY)

            if(cv.error > 0) {

                updateMessageText(HtmlCompat.fromHtml(cv.message ?: "Unknown error", 0).toString())

            } else {

                // and it's not invalid.
                updateMessageText(cv.message)

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

    // TODO: test visibility
    private fun updateMessageText(message: String?) {

        mainActivityMessage.text = message
        mainActivityMessage.visibility = if(message == null || message.isEmpty()){ View.GONE } else View.VISIBLE

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        applyFilter()

    }

    private fun applyFilter() {

        // cancel previous callback
        mainActivityCvSearchText.removeCallbacks(this.applyFilterCallBack)
        mainActivityCvSearchText.postDelayed(this.applyFilterCallBack, 300)

    }

    private val applyFilterCallBack by lazy {
        Runnable {
            applyFilterExecute()
        }
    }

    private fun applyFilterExecute() {
        cvModel?.filter(mainActivityCvSearchText?.text.toString())
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onClick(v: View?) {
        if (v == mainActivityCvSearchButton)
            applyFilterExecute()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

        if(v == mainActivityCvSearchText){

            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                applyFilterExecute()
                hideKeyboard()
                return  true
            }

        }

        return false

    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }

}
