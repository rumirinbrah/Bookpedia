package com.plcoding.bookpedia.feature_book.data.database.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object StringListTypeConverter {

    @TypeConverter
    fun decodeToString(value : String) : List<String>{
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun encodeToString(list : List<String>) : String{
        return Json.encodeToString(list)
    }

}