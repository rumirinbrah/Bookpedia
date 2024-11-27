package com.plcoding.bookpedia.feature_book.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {

    fun create() : RoomDatabase.Builder<BookDatabase>

}