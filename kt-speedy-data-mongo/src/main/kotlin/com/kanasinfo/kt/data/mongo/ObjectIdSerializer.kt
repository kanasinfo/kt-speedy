package com.kanasinfo.kt.data.mongo

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

import java.io.IOException

class ObjectIdSerializer : JsonSerializer<Any>() {
    @Throws(IOException::class)
    override fun serialize(value: Any, jsonGen: JsonGenerator, provider: SerializerProvider) {
        jsonGen.writeString(value.toString())
    }
}