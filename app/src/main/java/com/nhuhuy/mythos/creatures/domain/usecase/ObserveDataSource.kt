package com.nhuhuy.mythos.creatures.domain.usecase

import android.util.Log
import com.nhuhuy.mythos.core.utils.Result
import com.nhuhuy.mythos.core.utils.getDataOrNull
import com.nhuhuy.mythos.creatures.data.mapper.toModel
import com.nhuhuy.mythos.creatures.data.source.LocalCreatureSource
import com.nhuhuy.mythos.creatures.data.source.RemoteCreatureSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ObserveDataSource @Inject constructor(
    private val local: LocalCreatureSource,
    private val network: RemoteCreatureSource
) {
    operator fun invoke() = flow {
        val localData = local.getAll()
        localData.ifEmpty {
            val networkData = network.fetchCreatureList()
            val entities = networkData.getDataOrNull() ?: emptyList()
            emit(networkData)
            Log.d("Network Data", "$networkData")
            local.insertAll(entities)
            return@flow
        }
        val creatures = localData.map { entity -> entity.toModel() }
        Log.d("Local Data", "$creatures")
        emit(Result.Success(creatures))
    }
}