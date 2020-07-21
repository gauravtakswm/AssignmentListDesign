package com.gauravtak.assignment_list_design

import com.gauravtak.assignment_list_design.model_classes.ListDataResponse.RowsBean
import com.gauravtak.assignment_list_design.utils_classes.UtilHelper.isRowVisible
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ItemVisibilityUnitTest {
    @Before
    fun setup() {
        println("Ready for testing!")
    }

    @Test
    fun visibilityHiddenWithNullValues() {
        val rowsBean = RowsBean()
        rowsBean.title = null
        rowsBean.description = null
        rowsBean.imageHref = null
        Assert.assertEquals(false, isRowVisible(rowsBean))
    }

    @Test
    fun visibilityHiddenWithEmptyValues() {
        val rowsBean = RowsBean()
        rowsBean.title = " "
        rowsBean.description = ""
        rowsBean.imageHref = " "
        Assert.assertEquals(false, isRowVisible(rowsBean))
    }

    @Test
    fun visibilityHiddenWithNonEmptyValues1() {
        val rowsBean = RowsBean()
        rowsBean.title = "California"
        rowsBean.description = null
        rowsBean.imageHref = " "
        Assert.assertTrue(isRowVisible(rowsBean))
    }

    @Test
    fun visibilityHiddenWithNonEmptyValues2() {
        val rowsBean = RowsBean()
        rowsBean.title = ""
        rowsBean.description = "this is the capital of USA"
        rowsBean.imageHref = null
        Assert.assertTrue(isRowVisible(rowsBean))
    }

    @Test
    fun visibilityHiddenWithNonEmptyValues3() {
        val rowsBean = RowsBean()
        rowsBean.title = ""
        rowsBean.description = "https://www.cdc.gov/media/dpk/diseases-and-conditions/coronavirus/images/outbreak-coronavirus-world-1024x506px.jpg"
        rowsBean.imageHref = " "
        Assert.assertTrue(isRowVisible(rowsBean))
    }

    @After
    fun cleanup() {
        println("Done with unit test!")
    }
}