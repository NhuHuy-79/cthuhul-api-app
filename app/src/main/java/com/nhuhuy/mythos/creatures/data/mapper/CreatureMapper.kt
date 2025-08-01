package com.nhuhuy.mythos.creatures.data.mapper

import com.nhuhuy.mythos.creatures.data.local.room.CreatureEntity
import com.nhuhuy.mythos.creatures.data.network.CreatureDTO
import com.nhuhuy.mythos.creatures.domain.model.Creature

fun CreatureDTO.toModel() = Creature(
    author = this.author,
    canon = this.canon,
    category = this.category,
    id = this.id,
    img = this.img,
    name = this.name,
    nicks = this.nicks ?: emptyList(),
    overview = this.overview,
    wikiUrl = this.wikiUrl
)

fun Creature.toEntity() = CreatureEntity(
    author = this.author,
    canon = this.canon,
    category = this.category,
    id = this.id.toLong(),
    images = this.img,
    name = this.name,
    nicks = this.nicks.orEmpty(),
    overviews = this.overview,
    wikiUrl = this.wikiUrl
)


fun CreatureEntity.toModel() = Creature(
    author = this.author,
    id = this.id.toInt(),
    name = this.name,
    canon = this.canon,
    category = this.category,
    img = this.images,
    nicks = this.nicks,
    overview = this.overviews,
    wikiUrl = this.wikiUrl,
)

fun CreatureDTO.toEntity() = CreatureEntity(
    author = this.author,
    canon = this.canon,
    category = this.category,
    id = this.id.toLong(),
    images = this.img,
    name = this.name,
    nicks = this.nicks.orEmpty(),
    overviews = this.overview,
    wikiUrl = this.wikiUrl
)

