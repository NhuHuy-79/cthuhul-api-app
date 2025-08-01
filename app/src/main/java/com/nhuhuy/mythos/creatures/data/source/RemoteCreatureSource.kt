package com.nhuhuy.mythos.creatures.data.source

import android.util.Log
import com.nhuhuy.mythos.core.utils.Result
import com.nhuhuy.mythos.creatures.data.mapper.toModel
import com.nhuhuy.mythos.creatures.data.network.CreatureApi
import com.nhuhuy.mythos.creatures.data.network.CreatureDTO
import com.nhuhuy.mythos.creatures.domain.model.Creature
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class RemoteCreatureSource @Inject constructor(
    private val api: CreatureApi,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun fetchCreatureList(): Result<List<Creature>> {
        return withContext(dispatcher) {
            try {
                val response = api.getCreatureList().map {
                    it.toModel()
                }
                Result.Success(response)
            } catch (e: IOException) {
                Log.e("ExceptionRetrofit", e.toString())
                Result.Failure(e)
            } catch (e: HttpException) {
                Log.e("ExceptionRetrofit", e.toString())
                Result.Failure(e)
            } catch (e: SocketTimeoutException) {
                Log.e("ExceptionRetrofit", e.toString())
                Result.Failure(e)
            } catch (e: Exception) {
                Log.e("ExceptionRetrofit", e.toString())
                Result.Failure(e)
            }
        }
    }

    suspend fun getCreatureDetail(id: Int): Result<CreatureDTO> {
        return withContext(dispatcher) {
            try {
                val response = api.getCreatureDetail(id)
                Result.Success(response)
            } catch (e: IOException) {
                Log.e("ExceptionRetrofit", e.toString())
                Result.Failure(e)
            } catch (e: HttpException) {
                Log.e("ExceptionRetrofit", e.toString())
                Result.Failure(e)
            } catch (e: SocketTimeoutException) {
                Log.e("ExceptionRetroft", e.toString())
                Result.Failure(e)
            } catch (e: Exception) {
                Log.e("ExceptionRetrofit", e.toString())
                Result.Failure(e)
            }
        }
    }
}