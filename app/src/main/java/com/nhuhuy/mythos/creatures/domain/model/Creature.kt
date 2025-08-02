package com.nhuhuy.mythos.creatures.domain.model

import androidx.compose.runtime.Immutable

@Immutable
class Creature(
    val author: String,
    val canon: String,
    val category: String,
    val id: Int,
    val img: List<String>,
    val name: String,
    val nicks: List<String>,
    val overview: String,
    val wikiUrl: String
)


