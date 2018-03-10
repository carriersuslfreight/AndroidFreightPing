package com.uslfreight.carriers.util

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.StringReader


class XMLUtil(private val parser: XmlPullParser) {

    fun getTimerValue(xml: String, default: Long): Long{
        var timerValue = 0L
        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(StringReader(xml))
            while (parser.next() !== XmlPullParser.END_TAG) {
                if (parser.eventType !== XmlPullParser.START_TAG) {
                    continue
                }
                val name = parser.name
                if (name == "result") {
                    timerValue = readText(parser).toLong()
                }
            }
        }
        catch(e: Exception ) {
            //NOOP, return default
        }
        return if( timerValue != 0L ) timerValue else default
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }
}