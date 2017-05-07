package com.mjdinteractive.applymjd.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

inline fun <reified T : Any> arrayDeserializer(): JsonDeserializer<Array<T>> {
    return object : JsonDeserializer<Array<T>> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Array<T> {
            return if (json.isJsonArray) {
                val jsonArray = json.asJsonArray
                Array(jsonArray.size()) { i -> context.deserialize<T>(jsonArray[i], T::class.java) }
            } else if (json.isJsonObject) {
                Array(1) { context.deserialize<T>(json, T::class.java) }
            } else {
                throw JsonParseException("Expected valid object or object array")
            }
        }
    }
}

class DateDeserializer : JsonDeserializer<Date> {
    val fmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.US)

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date {
        val timestamp = json.asString

        try {
            return Date(timestamp.toLong() * 1000)
        } catch (e: Exception) {
        }
        try {
            return fmt.parse(timestamp)
        } catch (e: Exception) {
        }

        throw ParseException("Unknown date format $timestamp", 0)
    }
}