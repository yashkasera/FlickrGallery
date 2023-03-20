package com.yashkasera.flickrgallery.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yashkasera.flickrgallery.data.entity.Photo
import com.yashkasera.flickrgallery.data.entity.PhotoRemoteKey

@Database(entities = [Photo::class, PhotoRemoteKey::class], version = 1, exportSchema = false)
abstract class FlickrDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
    abstract fun photoRemoteKeyDao(): PhotoRemoteKeyDao
}