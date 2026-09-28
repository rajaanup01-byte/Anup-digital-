package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT serviceId FROM favorites ORDER BY addedAt DESC")
    fun getAllFavoriteIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE serviceId = :serviceId")
    suspend fun removeFavorite(serviceId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE serviceId = :serviceId)")
    fun isFavoriteFlow(serviceId: String): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE serviceId = :serviceId)")
    suspend fun isFavorite(serviceId: String): Boolean
}
