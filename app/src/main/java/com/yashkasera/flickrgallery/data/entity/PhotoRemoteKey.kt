package com.yashkasera.flickrgallery.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class PhotoRemoteKey(
    @PrimaryKey val id: String,
    val prevPage: Int?,
    val nextPage: Int?,
)