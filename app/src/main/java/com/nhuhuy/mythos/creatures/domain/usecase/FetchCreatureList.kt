package com.nhuhuy.mythos.creatures.domain.usecase

import com.nhuhuy.mythos.core.utils.Result
import com.nhuhuy.mythos.core.utils.getDataOrNull
import com.nhuhuy.mythos.creatures.data.mapper.toEntity
import com.nhuhuy.mythos.creatures.data.source.LocalCreatureSource
import com.nhuhuy.mythos.creatures.data.source.RemoteCreatureSource
import com.nhuhuy.mythos.creatures.domain.model.Creature
import javax.inject.Inject

class FetchCreatureList @Inject constructor(
    private val remote: RemoteCreatureSource,
    private val local: LocalCreatureSource
) {
    suspend operator fun invoke(): Result<List<Creature>> {
        val response = remote.fetchCreatureList()
        if (response is Result.Success){
            val list = response.data ?: emptyList()
            local.insertAll(list)
        }
        return response
    }
}
