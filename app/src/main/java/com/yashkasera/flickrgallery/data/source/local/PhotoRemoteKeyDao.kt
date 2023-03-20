package com.yashkasera.flickrgallery.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yashkasera.flickrgallery.data.entity.PhotoRemoteKey

@Dao
interface PhotoRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PhotoRemoteKey>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(remoteKey: PhotoRemoteKey)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getPhotoRemoteKey(id: String): PhotoRemoteKey

    @Query("DELETE FROM remote_keys")
    suspend fun clear()

    @Query("SELECT * FROM remote_keys")
    suspend fun getAll(): List<PhotoRemoteKey>

    @Query("SELECT * FROM remote_keys ORDER BY nextPage DESC LIMIT 1")
    suspend fun getRemoteKeyForLastItem(): PhotoRemoteKey?
}