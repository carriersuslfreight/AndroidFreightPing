package com.uslfreight.carriers.util

import android.support.test.runner.AndroidJUnit4
import android.util.Xml
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class XmlUtilTest {

    private val TAG = XmlUtilTest::class.java.simpleName
    private lateinit var xmlUtil: XMLUtil
    private var successXml = "<source><result>10</result></source>"
    private var failureXml = "<source></source>"
    private var invalidXml = "<source></source"
    private val defaultValue = 11L

    @Before
    fun setUp() {
        val parser = Xml.newPullParser()
        xmlUtil = XMLUtil(parser)
    }

    @Test
    fun testGetTimerValueSuccess() {
        val expected = 10L
        val actual = xmlUtil.getTimerValue(successXml, defaultValue)
        assertEquals("Unexpected value", expected, actual)
    }

    @Test
    fun testGetTimerValueFailure() {
        val expected = defaultValue
        val actual = xmlUtil.getTimerValue(failureXml, defaultValue)
        assertEquals("Unexpected value", expected, actual)
    }

    @Test
    fun testGetTimerValueInvalid() {
        val expected = defaultValue
        val actual = xmlUtil.getTimerValue(invalidXml, defaultValue)
        assertEquals("Unexpected value", expected, actual)
    }
}