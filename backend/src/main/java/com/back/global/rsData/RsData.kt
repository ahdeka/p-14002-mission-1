package com.back.global.rsData

import com.fasterxml.jackson.annotation.JsonIgnore

@JvmRecord
data class RsData<T>(
    val resultCode: String?,
    @JsonIgnore val statusCode: Int,
    val msg: String?,
    val data: T?
) {
    @JvmOverloads
    constructor(resultCode: String, msg: String?, data: T? = null) : this(
        resultCode,
        resultCode.substringBefore("-").toInt(),
        msg,
        data
    )
}