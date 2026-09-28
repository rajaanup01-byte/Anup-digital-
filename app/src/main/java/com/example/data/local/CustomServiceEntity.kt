package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_services")
data class CustomServiceEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val nameHindi: String,
    val categoryId: String,
    val categoryNameEn: String,
    val categoryNameHi: String,
    val description: String,
    val descriptionHindi: String,
    val officialUrl: String,
    val iconKey: String,
    val keywordsCsv: String,
    val isPopular: Boolean,
    val isLatest: Boolean,
    val isActive: Boolean,
    val stateScope: String,
    val requiredDocsHindi: String,
    val requiredDocsEnglish: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
