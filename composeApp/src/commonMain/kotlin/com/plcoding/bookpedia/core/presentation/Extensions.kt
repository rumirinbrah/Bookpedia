package com.plcoding.bookpedia.core.presentation

import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.disk_full_error
import cmp_bookpedia.composeapp.generated.resources.no_internet
import cmp_bookpedia.composeapp.generated.resources.request_timed_out
import cmp_bookpedia.composeapp.generated.resources.server_error
import cmp_bookpedia.composeapp.generated.resources.too_many_requests
import cmp_bookpedia.composeapp.generated.resources.unknown_error
import com.plcoding.bookpedia.core.domain.DataError

fun DataError.toUiText():UiText{
    val stringRes = when(this){
        DataError.LocalError.DISK_FULL->{
            Res.string.disk_full_error
        }
        DataError.LocalError.UNKNOWN->{
            Res.string.unknown_error
        }
        DataError.NetworkError.REQUEST_TIMED_OUT->{
            Res.string.request_timed_out
        }
        DataError.NetworkError.TOO_MANY_REQUESTS->{
            Res.string.too_many_requests
        }
        DataError.NetworkError.NO_INTERNET->{
            Res.string.no_internet
        }
        DataError.NetworkError.SERVER_ERROR->{
            Res.string.server_error
        }
        DataError.NetworkError.SERIALIZATION_ERROR->{
            Res.string.disk_full_error
        }
        DataError.NetworkError.UNKNOWN->{
            Res.string.unknown_error
        }
        else -> {
            Res.string.unknown_error
        }
    }
    return UiText.StringResourceId(stringRes)
}