package com.nhuhuy.mythos.creatures.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nhuhuy.mythos.creatures.data.local.room.converter.CreatureConverter

@Database(
    entities = [CreatureEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CreatureConverter::class)
abstract class CreatureDatabase : RoomDatabase() {
    abstract val creatureDao: CreatureDao
}