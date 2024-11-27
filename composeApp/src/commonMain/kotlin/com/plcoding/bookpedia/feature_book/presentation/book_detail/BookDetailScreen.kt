package com.plcoding.bookpedia.feature_book.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.core.presentation.SandYellow
import com.plcoding.bookpedia.feature_book.presentation.book_detail.components.BlurredImageBackground
import com.plcoding.bookpedia.feature_book.presentation.book_detail.components.Chip
import com.plcoding.bookpedia.feature_book.presentation.book_detail.components.ChipSize
import com.plcoding.bookpedia.feature_book.presentation.book_detail.components.TitledChip
import kotlin.math.round

@Composable
fun BookDetailScreenRoot(
    bookDetailViewModel: BookDetailViewModel ,
    onBack: () -> Unit ,
) {
    val state by bookDetailViewModel.bookDetailState.collectAsStateWithLifecycle()
    BookDetailScreen(
        state = state ,
        onAction = {
            if (it is BookDetailAction.OnBackClick) {
                onBack()
            }
            bookDetailViewModel.onAction(it)
        }
    )

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BookDetailScreen(
    state: BookDetailState ,
    onAction: (BookDetailAction) -> Unit
) {


    val scrollState = rememberScrollState()


    BlurredImageBackground(
        imageUrl = state.book?.imageUrl ,
        isFavourite = state.isFavourite ,
        onFavouriteClick = {
            onAction(BookDetailAction.OnFavouriteClick)
        } ,
        onBack = {
            onAction(BookDetailAction.OnBackClick)
        }
    ) {
        if (state.book != null) {
            val book = state.book
            Column(
                Modifier.widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState) ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    book.title ,
                    style = MaterialTheme.typography.titleLarge ,
                    textAlign = TextAlign.Center
                )
                Text(book.authors.first() , style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(10.dp))
                //RATING AND PGS
                Row(
                    Modifier.wrapContentWidth() ,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    TitledChip(
                        title = "Rating"
                    ) {
                        Chip(
                            size = ChipSize.REGULAR
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically ,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                book.averageRating?.let {
                                    val rating = round(book.averageRating * 10) / 10.0
                                    Text("$rating")
                                }
                                Icon(
                                    imageVector = Icons.Default.Star ,
                                    contentDescription = null ,
                                    tint = SandYellow
                                )
                            }
                        }
                    }
                    TitledChip(
                        title = "Pages"
                    ) {
                        Chip {
                            Text("${book.numPages}")
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))

                //LANGUAGE
                TitledChip(
                    title = "Language" ,
                ) {
                    FlowRow (
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.Center
                    ){
                        book.languages.forEach {lang->
                            Chip(
                                size = ChipSize.SMALL,
                                modifier = Modifier.padding(5.dp)
                            ) { Text(lang) }
                        }
                    }

                }

                //SYNOPSIS
                Text(
                    "Synopsis" ,
                    fontSize = 20.sp ,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top=20.dp, bottom = 10.dp)
                )
                if(state.isLoading){
                    Row (
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        CircularProgressIndicator()
                    }
                }
                else{
                    if(book.description==null){
                        Text("Nothing available for this book :(", style = MaterialTheme.typography.bodyLarge)
                    }else{
                        Text("${book.description}", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Start)
                    }
                }

            }
        }


    }
}



