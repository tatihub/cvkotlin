package com.juiceos.kotlincv.db.entity

class CVEntity(
    val cvId : String,
    val title : String
){
    val sections: MutableList<CVSectionEntity> = ArrayList()
}