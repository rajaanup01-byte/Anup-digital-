package com.example.data.model

data class ServiceItem(
    val id: String,
    val name: String,
    val nameHindi: String,
    val categoryId: String,
    val categoryNameEn: String,
    val categoryNameHi: String,
    val description: String,
    val descriptionHindi: String,
    val officialUrl: String,
    val iconKey: String = "public",
    val keywords: List<String> = emptyList(),
    val isPopular: Boolean = false,
    val isLatest: Boolean = false,
    val isActive: Boolean = true,
    val stateScope: String = "All India", // "Bihar" or "Central" or "All India"
    val requiredDocsHindi: String = "आधार कार्ड, पासपोर्ट साइज़ फोटो, मोबाइल नंबर",
    val requiredDocsEnglish: String = "Aadhaar Card, Passport Size Photo, Mobile Number",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    fun matchesQuery(query: String): Boolean {
        if (query.isBlank()) return true
        val q = query.trim().lowercase()
        return name.lowercase().contains(q) ||
                nameHindi.lowercase().contains(q) ||
                categoryNameEn.lowercase().contains(q) ||
                categoryNameHi.lowercase().contains(q) ||
                description.lowercase().contains(q) ||
                descriptionHindi.lowercase().contains(q) ||
                officialUrl.lowercase().contains(q) ||
                stateScope.lowercase().contains(q) ||
                keywords.any { it.lowercase().contains(q) }
    }
}

data class CategoryItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val iconKey: String,
    val colorHex: Long = 0xFF0D47A1,
    val descriptionHi: String,
    val descriptionEn: String,
    val isActive: Boolean = true
)

data class AnnouncementItem(
    val id: String,
    val title: String,
    val titleHindi: String,
    val message: String,
    val messageHindi: String,
    val tag: String = "New",
    val url: String? = null,
    val date: String = "Today",
    val isActive: Boolean = true
)

enum class AppLanguage {
    HINDI,
    ENGLISH
}
