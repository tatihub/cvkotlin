package com.juiceos.kotlincv.db.entity

import androidx.room.PrimaryKey

data class CVEntity(
    @PrimaryKey val cvId : String,
    val title : String
)