package com.plcoding.bookpedia.feature_book.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plcoding.bookpedia.feature_book.data.database.converter.StringListTypeConverter

@Database(
    exportSchema = false,
    version = 1,
    entities = [BookEntity::class]
)
@TypeConverters(StringListTypeConverter::class)
@ConstructedBy(BookDatabaseConstructor::class)
abstract class BookDatabase : RoomDatabase() {

    abstract val bookDao : BookDao

    companion object{
        const val DB_NAME="book_db"
    }
}