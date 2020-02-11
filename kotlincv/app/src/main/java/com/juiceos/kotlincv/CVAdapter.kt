package com.juiceos.kotlincv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.juiceos.kotlincv.db.entity.CVEntity
import kotlinx.android.synthetic.main.cv_section_layout.view.*

class CVAdapter : RecyclerView.Adapter<CVAdapter.CVViewHolder>(){

    lateinit var cv : CVEntity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CVViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cv_section_layout, parent, false) as ViewGroup

        return CVViewHolder(itemView)


    }

    override fun getItemCount() = cv.sections.size

    override fun onBindViewHolder(holder: CVViewHolder, position: Int) {

        val section = cv.sections[position]
        holder.cvSectionLayout.cvSectionDate.text = HtmlCompat.fromHtml(section.date, HtmlCompat.FROM_HTML_MODE_LEGACY)
        holder.cvSectionLayout.cvSectionTitle.text = HtmlCompat.fromHtml(section.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
        holder.cvSectionLayout.cvSectionDetails.text = HtmlCompat.fromHtml(section.details, HtmlCompat.FROM_HTML_MODE_LEGACY)

    }

    class CVViewHolder(val cvSectionLayout : ViewGroup) : RecyclerView.ViewHolder(cvSectionLayout)


}