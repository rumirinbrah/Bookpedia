package com.plcoding.bookpedia.feature_book.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.bookpedia.feature_book.domain.Book

@Composable
fun BookList(
    books: List<Book> ,
    onBookClick: (Book) -> Unit ,
    lazyState: LazyListState = rememberLazyListState() ,
    modifier: Modifier = Modifier
) {

    LazyColumn (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        state = lazyState,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(
            items = books,
            key = { it.id }
        ){book->
            BookListItem(
                modifier = Modifier.widthIn(max=700.dp)
                    .fillMaxWidth(),
                book = book ,
                onClick = {
                    onBookClick(book)
                }

            )
        }
    }



}