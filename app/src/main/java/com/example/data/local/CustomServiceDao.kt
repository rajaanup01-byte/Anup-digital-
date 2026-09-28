package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomServiceDao {
    @Query("SELECT * FROM custom_services ORDER BY createdAt DESC")
    fun getAllCustomServices(): Flow<List<CustomServiceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: CustomServiceEntity)

    @Update
    suspend fun updateService(service: CustomServiceEntity)

    @Delete
    suspend fun deleteService(service: CustomServiceEntity)

    @Query("DELETE FROM custom_services WHERE id = :id")
    suspend fun deleteById(id: String)
}
