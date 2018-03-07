package com.uslfreight.carriers.network

interface NetworkResponseCallback {
    fun onSuccess(message: String)
    fun onFailure(message: String)
}