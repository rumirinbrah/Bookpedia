package com.plcoding.bookpedia.feature_book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.favourites
import cmp_bookpedia.composeapp.generated.resources.search_results
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DesertWhite
import com.plcoding.bookpedia.core.presentation.PulseAnimation
import com.plcoding.bookpedia.core.presentation.SandYellow
import com.plcoding.bookpedia.feature_book.domain.Book
import com.plcoding.bookpedia.feature_book.presentation.book_list.components.BookList
import com.plcoding.bookpedia.feature_book.presentation.book_list.components.SearchBar
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@Composable
fun BookListScreenRoot(
    bookListViewModel: BookListViewModel ,
    onBookClick: (Book) -> Unit
) {
    val state by bookListViewModel.bookListState.collectAsStateWithLifecycle()
    BookListScreen(
        state = state ,
        onAction = { action ->
            when (action) {
                is BookListAction.OnBookClick -> {
                    onBookClick(action.book)
                }

                else -> Unit
            }
            bookListViewModel.onAction(action)
        }
    )
}


@Composable
fun BookListScreen(
    state: BookListState ,
    onAction: (BookListAction) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val pagerState = rememberPagerState { 2 }

    val searchListState = rememberLazyListState()
    val favouriteListState = rememberLazyListState()

    LaunchedEffect(state.searchQuery){
        delay(2000)
        searchListState.animateScrollToItem(0)
    }
    LaunchedEffect(pagerState.currentPage){
        onAction(BookListAction.OnTabChange(pagerState.currentPage))
    }
    LaunchedEffect(state.selectedTabIndex){
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }
    Column(
        Modifier.fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding() ,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        //search bar
        SearchBar(
            modifier = Modifier.widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp) ,
            searchQuery = state.searchQuery ,
            onSearchQueryChange = { query ->
                onAction(BookListAction.OnSearchQuery(query))
            } ,
            onImeSearch = {
                keyboardController?.hide()
            }
        )


        Surface(
            Modifier.fillMaxWidth()
                .weight(1f)
                .fillMaxWidth() ,
            color = DesertWhite ,
            shape = RoundedCornerShape(topStart = 25.dp , topEnd = 25.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //TAB
                TabRow(
                    selectedTabIndex = state.selectedTabIndex ,
                    modifier = Modifier.padding(vertical = 10.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth() ,
                    containerColor = DesertWhite ,
                    contentColor = SandYellow ,
                    indicator = {
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow ,
                            modifier = Modifier
                                .tabIndicatorOffset(it[state.selectedTabIndex])
                        )
                    }//to change the indicator color
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0 ,
                        onClick = {
                            onAction(BookListAction.OnTabChange(0))
                        } ,
                        selectedContentColor = SandYellow ,
                        unselectedContentColor = Color.Gray
                    ) {
                        Text(
                            text = stringResource(Res.string.search_results) ,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    Tab(
                        selected = state.selectedTabIndex == 1 ,
                        onClick = {
                            onAction(BookListAction.OnTabChange(1))
                        } ,
                        selectedContentColor = SandYellow ,
                        unselectedContentColor = Color.Gray
                    ) {
                        Text(
                            text = stringResource(Res.string.favourites) ,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }

                // --PAGER--
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                        .weight(1f)
                ){page->

                        when(page){
                            0->{
                                //SHOW RESULT
                                if(state.isLoading){
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ){
                                        PulseAnimation(Modifier.size(60.dp))
                                    }

                                }else{
                                    when{
                                        state.errorMessage!=null->{
                                            Box(
                                                modifier = Modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ){
                                                Text(
                                                    text = state.errorMessage.asString(),
                                                    fontSize = 15.sp,
                                                    textAlign = TextAlign.Center,
                                                    color = MaterialTheme.colorScheme.error
                                                )
                                            }

                                        }
                                        state.searchResult.isEmpty()->{
                                            Box(
                                                modifier = Modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ){
                                                Text(
                                                    text = "Nothing found T-T",
                                                    fontSize = 15.sp,
                                                    textAlign = TextAlign.Center,
                                                    color = MaterialTheme.colorScheme.error
                                                )
                                            }

                                        }
                                        else->{
                                            Box(
                                                modifier = Modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ){
                                                BookList(
                                                    books = state.searchResult ,
                                                    onBookClick = {book->
                                                        onAction(BookListAction.OnBookClick(book))
                                                    },
                                                    lazyState = favouriteListState
                                                )
                                            }

                                        }
                                    }
                                }
                            }
                            1->{
                                //FAVS
                                if(state.favouriteBooks.isEmpty()){
                                    Box(
                                        Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(
                                            text = "You dont have any favourites",
                                            fontSize = 15.sp,
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }

                                }else{
                                    Box(
                                        Modifier.fillMaxSize()
                                    ){
                                        BookList(
                                            books = state.favouriteBooks ,
                                            onBookClick = {book->
                                                onAction(BookListAction.OnBookClick(book))
                                            },
                                            modifier = Modifier,
                                            lazyState = favouriteListState
                                        )
                                    }

                                }
                            }
                        }


                }


            }
        }


    }
}





