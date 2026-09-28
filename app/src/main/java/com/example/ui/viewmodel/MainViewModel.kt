package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.AnnouncementItem
import com.example.data.model.AppLanguage
import com.example.data.model.CategoryItem
import com.example.data.model.ServiceItem
import com.example.data.repository.ServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ServiceRepository
) : ViewModel() {

    // Default language is Hindi as requested
    private val _language = MutableStateFlow(AppLanguage.HINDI)
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()

    val categories: List<CategoryItem> = repository.categories
    val announcements: List<AnnouncementItem> = repository.announcements

    val allServices: StateFlow<List<ServiceItem>> = repository.allServicesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val favoriteIds: StateFlow<Set<String>> = repository.favoriteIdsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    val favoriteServices: StateFlow<List<ServiceItem>> = repository.favoriteServicesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val searchResults: StateFlow<List<ServiceItem>> = combine(
        allServices,
        _searchQuery,
        _selectedCategoryId
    ) { services, query, catId ->
        var list = services
        if (!catId.isNullOrEmpty()) {
            list = list.filter { it.categoryId == catId }
        }
        if (query.isNotBlank()) {
            list = list.filter { it.matchesQuery(query) }
        }
        list
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun toggleLanguage() {
        _language.value = if (_language.value == AppLanguage.HINDI) AppLanguage.ENGLISH else AppLanguage.HINDI
    }

    fun setLanguage(lang: AppLanguage) {
        _language.value = lang
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(categoryId: String?) {
        _selectedCategoryId.value = categoryId
    }

    fun toggleFavorite(serviceId: String) {
        viewModelScope.launch {
            repository.toggleFavorite(serviceId)
        }
    }

    fun getServiceById(id: String): ServiceItem? {
        return allServices.value.find { it.id == id }
    }

    fun addService(service: ServiceItem) {
        viewModelScope.launch {
            repository.addCustomService(service)
        }
    }

    fun deleteService(id: String) {
        viewModelScope.launch {
            repository.deleteCustomService(id)
        }
    }
}

class MainViewModelFactory(
    private val repository: ServiceRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
