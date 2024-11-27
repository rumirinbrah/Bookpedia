package com.plcoding.bookpedia.dInjection

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.plcoding.bookpedia.core.data.networking.HttpClientFactory
import com.plcoding.bookpedia.feature_book.data.database.BookDatabase
import com.plcoding.bookpedia.feature_book.data.database.DatabaseFactory
import com.plcoding.bookpedia.feature_book.data.local.RoomDataSource
import com.plcoding.bookpedia.feature_book.data.networking.KtorDataSource
import com.plcoding.bookpedia.feature_book.domain.DataSource
import com.plcoding.bookpedia.feature_book.domain.FavouriteBookRepo
import com.plcoding.bookpedia.feature_book.presentation.book_detail.BookDetailScreenRoot
import com.plcoding.bookpedia.feature_book.presentation.book_list.BookListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import com.plcoding.bookpedia.feature_book.presentation.book_detail.BookDetailViewModel
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule : Module

val sharedModule = module {

    single {
        HttpClientFactory.create(
            get()
        )
    }


    singleOf ( ::KtorDataSource).bind<DataSource>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<BookDatabase>().bookDao }
    singleOf(::RoomDataSource).bind<FavouriteBookRepo>()

    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
}