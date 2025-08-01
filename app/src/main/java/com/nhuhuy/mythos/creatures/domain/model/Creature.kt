package com.nhuhuy.mythos.creatures.domain.model

import androidx.compose.runtime.Immutable
import java.util.Locale

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


fun String.capitalizeName(): String {
    val charArray = this.split(" ")
        .map { char ->
            char.trim('"')
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() }
        }
    return charArray.joinToString(" ")
}

fun List<String>.capitalizeName(): String {
    val charArray = this
        .map { char ->
            char.trim('"')
                .capitalizeName()
        }
    return charArray.joinToString(", ")
}

fun List<Creature>.filterCategory(category: String): List<Creature> {
    return this.filter { list ->
        list.category.contains(category, ignoreCase = true)
    }
}

fun List<Creature>.filterName(query: String): List<Creature> {

    if (query.isBlank()) return this

    return this.filter { list ->
        list.name.contains(query, ignoreCase = true)
    }
}


