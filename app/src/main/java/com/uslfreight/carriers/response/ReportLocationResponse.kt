package com.uslfreight.carriers.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "source", strict = false)
class ReportLocationResponse {

    @Element(name = "result")
    private var result: String? = null

    fun getResult(): String? {
        return result
    }

    fun setResult(result: String) {
        this.result = result
    }
}