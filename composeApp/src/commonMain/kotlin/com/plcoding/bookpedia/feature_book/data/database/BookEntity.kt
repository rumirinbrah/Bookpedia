package com.plcoding.bookpedia.feature_book.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class BookEntity(
    @PrimaryKey(autoGenerate = false) val id : String,
    val title : String,
    val description : String?,
    val imageUrl : String,
    val languages : List<String>,
    val authors : List<String>,
    val firstPublishYear : String?,
    val averageRating : Double?,
    val ratingCount : Int?,
    val numPages : Int,
    val numEdition : Int,
)
