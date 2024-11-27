package com.plcoding.bookpedia.feature_book.data.database

import androidx.room.RoomDatabaseConstructor

//not sure how this works
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor : RoomDatabaseConstructor<BookDatabase>{
    override fun initialize(): BookDatabase
}