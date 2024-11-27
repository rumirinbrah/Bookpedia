package com.plcoding.bookpedia.core.data.networking

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> responseToResult(
    response : HttpResponse
): Result<T,DataError.NetworkError>{
    return when(response.status.value){
        in 200..299->{
            try {
                Result.Success(response.body<T>())
            }catch (e : NoTransformationFoundException){
                Result.Error(DataError.NetworkError.SERIALIZATION_ERROR)
            }
        }
        408->{
            Result.Error(DataError.NetworkError.REQUEST_TIMED_OUT)
        }
        429 ->{
            Result.Error(DataError.NetworkError.TOO_MANY_REQUESTS)
        }
        in 500..599->{
            Result.Error(DataError.NetworkError.SERVER_ERROR)
        }
        else->{
            Result.Error(DataError.NetworkError.UNKNOWN)
        }
    }
}