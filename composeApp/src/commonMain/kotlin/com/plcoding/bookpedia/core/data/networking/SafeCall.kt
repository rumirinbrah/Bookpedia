package com.plcoding.bookpedia.core.data.networking

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
) : Result<T,DataError.NetworkError>{
    val response = try {
        execute()
    }catch (e : UnresolvedAddressException){
        return Result.Error(DataError.NetworkError.NO_INTERNET)
    }catch (e : SerializationException){
        return Result.Error(DataError.NetworkError.SERIALIZATION_ERROR)
    }catch (e : Exception){
        coroutineContext.ensureActive()
        return Result.Error(DataError.NetworkError.UNKNOWN)
    }

    return responseToResult(response)
}