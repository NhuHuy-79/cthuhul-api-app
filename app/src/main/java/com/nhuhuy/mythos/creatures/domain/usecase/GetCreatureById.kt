package com.nhuhuy.mythos.creatures.domain.usecase

import com.nhuhuy.mythos.creatures.data.mapper.toModel
import com.nhuhuy.mythos.creatures.data.source.LocalCreatureSource
import com.nhuhuy.mythos.creatures.domain.model.Creature
import javax.inject.Inject

class GetCreatureById @Inject constructor(
    private val local: LocalCreatureSource
) {
    suspend operator fun invoke(id: Int): Creature {
        return local.getCreatureById(id).toModel()
    }
}