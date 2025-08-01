package com.nhuhuy.mythos.creatures.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "creatures")
data class CreatureEntity(
    @PrimaryKey
    val id: Long = -1,
    val author: String = "",
    val canon: String = "",
    val name: String = "",
    val category: String = "",
    val nicks: List<String>,
    val images: List<String>,
    val overviews: String = "",
    val wikiUrl: String = ""
)
