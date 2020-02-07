package com.juiceos.kotlincv.db.entity

class CVEntity {

    var title: String? = null
    var error: Int = 0
    var errorMessage : String? = null

    val sections: MutableList<CVSectionEntity> = ArrayList()

}