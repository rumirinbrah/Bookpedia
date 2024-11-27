package com.plcoding.bookpedia.feature_book.presentation.book_list

import com.plcoding.bookpedia.feature_book.domain.Book

sealed class BookListAction {

    data class OnSearchQuery(val query :String) : BookListAction()
    data class OnBookClick(val book: Book) : BookListAction()
    data class OnTabChange(val index: Int) : BookListAction()

}