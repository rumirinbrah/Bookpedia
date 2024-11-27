package com.plcoding.bookpedia.core.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object BookListScreen : Screen()

    @Serializable
    data class BookDetailScreen(val id : String) : Screen()

}

