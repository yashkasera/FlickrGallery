package com.yashkasera.flickrgallery.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yashkasera.flickrgallery.data.entity.Photo

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(photos: List<Photo>)

    @Query("SELECT * FROM Photo")
    fun getAll(): PagingSource<Int, Photo>

    @Query("DELETE FROM Photo")
    suspend fun clear()
}