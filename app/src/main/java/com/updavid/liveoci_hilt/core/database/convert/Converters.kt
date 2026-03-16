package com.updavid.liveoci_hilt.core.database.convert

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromListIntToString(intList: List<Int>): String {
        return intList.joinToString(",")
    }

    @TypeConverter
    fun fromStringToListInt(stringList: String): List<Int> {
        if (stringList.isEmpty()) return emptyList()
        return stringList.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun fromListStringToString(stringList: List<String>): String {
        return stringList.joinToString("||")
    }

    @TypeConverter
    fun fromStringToListString(joinedString: String): List<String> {
        if (joinedString.isEmpty()) return emptyList()
        return joinedString.split("||")
    }
}