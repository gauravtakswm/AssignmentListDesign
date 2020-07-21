package com.gauravtak.assignment_list_design

import com.gauravtak.assignment_list_design.utils_classes.UtilHelper.isImageViewVisible
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ItemImageVisibilityTest {
    @Before
    fun setup() {
        println("Ready for testing!")
    }

    @Test
    fun visibilityHiddenWithNullUrl() {
        val url: String? = null
        Assert.assertEquals(false, isImageViewVisible(url))
    }

    @Test
    fun visibilityHiddenWithEmptyUrl() {
        val url = " "
        Assert.assertEquals(false, isImageViewVisible(url))
    }

    @Test
    fun visibilityCorrectWithValidUrl() {
        val url = "https://www.cdc.gov/media/dpk/diseases-and-conditions/coronavirus/images/outbreak-coronavirus-world-1024x506px.jpg"
        Assert.assertTrue(isImageViewVisible(url))
    }

    @After
    fun cleanup() {
        println("Done with unit test!")
    }

}