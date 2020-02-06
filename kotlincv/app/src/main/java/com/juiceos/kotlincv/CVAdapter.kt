package com.juiceos.kotlincv

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juiceos.kotlincv.db.entity.CVEntity
import kotlinx.android.synthetic.main.cv_section_layout.view.*

class CVAdapter : RecyclerView.Adapter<CVAdapter.CVViewHolder>(){

    lateinit var cv : CVEntity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CVViewHolder {

        // create a new view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cv_section_layout, parent, false) as ViewGroup
        // set the view's size, margins, paddings and layout parameters

        return CVViewHolder(itemView)


    }

    override fun getItemCount() = cv.sections.size

    override fun onBindViewHolder(holder: CVViewHolder, position: Int) {

        val section = cv.sections[position]
        holder.cvSectionLayout.cvSectionDate.text = section.date
        holder.cvSectionLayout.cvSectionTitle.text = section.title
        holder.cvSectionLayout.cvSectionDetails.text = section.details

    }

    class CVViewHolder(val cvSectionLayout : ViewGroup) : RecyclerView.ViewHolder(cvSectionLayout)


}