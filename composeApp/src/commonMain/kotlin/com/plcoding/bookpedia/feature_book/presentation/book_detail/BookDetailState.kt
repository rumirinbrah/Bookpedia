package com.plcoding.bookpedia.feature_book.presentation.book_detail

import com.plcoding.bookpedia.feature_book.domain.Book

data class BookDetailState(
    val isLoading : Boolean = true,
    val isFavourite : Boolean = false,
    val book: Book? = null

)
