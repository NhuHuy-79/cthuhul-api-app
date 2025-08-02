package com.nhuhuy.mythos.core.utils

import com.nhuhuy.mythos.creatures.domain.model.Creature
import java.util.Locale

fun String.capitalizeName(): String {
    val charArray = this.split(" ")
        .map { char ->
            char.trim('"')
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() }
        }
    return charArray.joinToString(" ")
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