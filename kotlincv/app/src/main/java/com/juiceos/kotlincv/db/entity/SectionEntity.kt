package com.juiceos.kotlincv.db.entity

import androidx.room.PrimaryKey

data class  SectionEntity(
    @PrimaryKey val sectionId : String,
    val title : String
)
