package com.hamza.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author by Ameer Hamza on 03/09/2023
 */
@Entity(tableName = "movies_remote_keys")
data class MovieRemoteKeyDbData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val prevPage: Int?,
    val nextPage: Int?
)