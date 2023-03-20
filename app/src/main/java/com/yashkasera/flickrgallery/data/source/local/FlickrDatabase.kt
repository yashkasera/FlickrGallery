package com.yashkasera.flickrgallery.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yashkasera.flickrgallery.data.entity.Photo

@Database(entities = [Photo::class], version = 1, exportSchema = false)
abstract class FlickrDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}