package com.example.data.repository

import com.example.data.datasource.DefaultServicesData
import com.example.data.local.CustomServiceDao
import com.example.data.local.CustomServiceEntity
import com.example.data.local.FavoriteDao
import com.example.data.local.FavoriteEntity
import com.example.data.model.AnnouncementItem
import com.example.data.model.CategoryItem
import com.example.data.model.ServiceItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class ServiceRepository(
    private val favoriteDao: FavoriteDao,
    private val customServiceDao: CustomServiceDao
) {
    val categories: List<CategoryItem> = DefaultServicesData.categories
    val announcements: List<AnnouncementItem> = DefaultServicesData.announcements

    // Combine default static services with locally created/custom services
    val allServicesFlow: Flow<List<ServiceItem>> = customServiceDao.getAllCustomServices()
        .map { customEntities ->
            val customServices = customEntities.map { entity ->
                ServiceItem(
                    id = entity.id,
                    name = entity.name,
                    nameHindi = entity.nameHindi,
                    categoryId = entity.categoryId,
                    categoryNameEn = entity.categoryNameEn,
                    categoryNameHi = entity.categoryNameHi,
                    description = entity.description,
                    descriptionHindi = entity.descriptionHindi,
                    officialUrl = entity.officialUrl,
                    iconKey = entity.iconKey,
                    keywords = entity.keywordsCsv.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                    isPopular = entity.isPopular,
                    isLatest = entity.isLatest,
                    isActive = entity.isActive,
                    stateScope = entity.stateScope,
                    requiredDocsHindi = entity.requiredDocsHindi,
                    requiredDocsEnglish = entity.requiredDocsEnglish,
                    createdAt = entity.createdAt,
                    updatedAt = entity.updatedAt
                )
            }
            // Merge custom services (which can override defaults by ID) with default services
            val customIds = customServices.map { it.id }.toSet()
            val filteredDefaults = DefaultServicesData.services.filterNot { it.id in customIds }
            (customServices + filteredDefaults).filter { it.isActive }
        }

    val favoriteIdsFlow: Flow<Set<String>> = favoriteDao.getAllFavoriteIds()
        .map { it.toSet() }

    // Combine all active services with favorites
    val favoriteServicesFlow: Flow<List<ServiceItem>> = combine(allServicesFlow, favoriteIdsFlow) { services, favIds ->
        services.filter { favIds.contains(it.id) }
    }

    suspend fun toggleFavorite(serviceId: String) {
        val isFav = favoriteDao.isFavorite(serviceId)
        if (isFav) {
            favoriteDao.removeFavorite(serviceId)
        } else {
            favoriteDao.addFavorite(FavoriteEntity(serviceId = serviceId))
        }
    }

    suspend fun isFavorite(serviceId: String): Boolean {
        return favoriteDao.isFavorite(serviceId)
    }

    fun isFavoriteFlow(serviceId: String): Flow<Boolean> {
        return favoriteDao.isFavoriteFlow(serviceId)
    }

    suspend fun addCustomService(service: ServiceItem) {
        val entity = CustomServiceEntity(
            id = service.id,
            name = service.name,
            nameHindi = service.nameHindi,
            categoryId = service.categoryId,
            categoryNameEn = service.categoryNameEn,
            categoryNameHi = service.categoryNameHi,
            description = service.description,
            descriptionHindi = service.descriptionHindi,
            officialUrl = service.officialUrl,
            iconKey = service.iconKey,
            keywordsCsv = service.keywords.joinToString(","),
            isPopular = service.isPopular,
            isLatest = service.isLatest,
            isActive = service.isActive,
            stateScope = service.stateScope,
            requiredDocsHindi = service.requiredDocsHindi,
            requiredDocsEnglish = service.requiredDocsEnglish,
            createdAt = service.createdAt,
            updatedAt = System.currentTimeMillis()
        )
        customServiceDao.insertService(entity)
    }

    suspend fun deleteCustomService(serviceId: String) {
        customServiceDao.deleteById(serviceId)
    }
}
