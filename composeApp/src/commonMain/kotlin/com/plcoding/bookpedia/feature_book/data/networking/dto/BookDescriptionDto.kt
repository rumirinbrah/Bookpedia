package com.plcoding.bookpedia.feature_book.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable(with = BookDescDtoSerializer::class)
data class BookDescriptionDto(
    val description : String? = null
)
