package com.plcoding.bookpedia.feature_book.data.database

import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<BookDatabase> {
        val dbFile = documentDirectory() + "/${BookDatabase.DB_NAME}"
        return Room.databaseBuilder<BookDatabase>(name=dbFile)
    }

    private fun documentDirectory() : String{
        val docDir = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory ,
            inDomain = NSUserDomainMask ,
            appropriateForURL = null,
            create = false,
            error = null
        )
        return requireNotNull(docDir?.path)
    }

}