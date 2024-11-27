package com.plcoding.bookpedia.feature_book.presentation.book_detail

import com.plcoding.bookpedia.feature_book.domain.Book

sealed class BookDetailAction {

    data object OnBackClick : BookDetailAction()

    data object OnFavouriteClick : BookDetailAction()

    data class OnSelectedBookChange(val book : Book) : BookDetailAction()

}