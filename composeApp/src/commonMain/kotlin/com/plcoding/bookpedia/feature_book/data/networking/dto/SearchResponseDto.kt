package com.plcoding.bookpedia.feature_book.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("docs") val books : List<SearchedBookDto>
)
