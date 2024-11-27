package com.plcoding.bookpedia.core.domain

interface DataError : Error {

    enum class NetworkError : DataError{
        NO_INTERNET,
        REQUEST_TIMED_OUT,
        TOO_MANY_REQUESTS,
        SERVER_ERROR,
        SERIALIZATION_ERROR,
        UNKNOWN
    }

    enum class LocalError : DataError{
        DISK_FULL,
        UNKNOWN
    }

}