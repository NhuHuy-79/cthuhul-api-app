package com.nhuhuy.mythos.creatures.data.local.room.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class CreatureConverter {
    @TypeConverter
    fun fromStringToList(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromListToString(value: List<String>): String {
        return Json.encodeToString(value)
    }
}