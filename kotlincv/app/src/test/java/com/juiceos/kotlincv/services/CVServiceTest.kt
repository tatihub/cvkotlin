package com.juiceos.kotlincv.services

import com.juiceos.kotlincv.db.entity.CVEntity
import com.juiceos.kotlincv.db.entity.CVSectionEntity
import org.junit.Test

class CVServiceTest{

    @Test
    fun apply_cv_filter_correctly () {

        assert(CVService.applyCVFilter(getCv(), null)?.sections?.size ?: 0 == 3)
        assert(CVService.applyCVFilter(getCv(), "")?.sections?.size ?: 0 == 3)
        assert(CVService.applyCVFilter(getCv(), "dEskToP")?.sections?.size ?: 0 == 1)
        assert(CVService.applyCVFilter(getCv(), "mObIle")?.sections?.size ?: 0 == 2)
        assert(CVService.applyCVFilter(getCv(), "aNdRoId")?.sections?.size ?: 0 == 1)
        assert(CVService.applyCVFilter(getCv(), "iPhOnE")?.sections?.size ?: 0 == 1)

    }

    private fun getCv(): CVEntity? {

        val cv = CVEntity()

        val section1 = CVSectionEntity("July 1, 2019", "Android section")
        val section2 = CVSectionEntity("July 1, 2019", "iPhone section")
        val section3 = CVSectionEntity("July 1, 2019", "Windows section")

        section1.details = "This is an android section. it's also mobile"
        section2.details = "This is an iPhone section. it's also mobile"
        section3.details = "This is a windows section. It's desktop"

        cv.sections.add(section1)
        cv.sections.add(section2)
        cv.sections.add(section3)

        return cv

    }

    @Test
    fun fount_text_as_expected(){

        val hay = "Trying to find aNdRoId In this text"
        val needle = "AndroiD"

        assert(CVService.foundText(hay, needle))
        assert(!CVService.foundText(hay, null))
        assert(!CVService.foundText(null, needle))
        assert(!CVService.foundText(hay, ""))
        assert(!CVService.foundText("", needle))

    }

}