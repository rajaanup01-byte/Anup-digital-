package com.example.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.components.AppTopHeader
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.CategoryDetailScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.ServiceDetailScreen
import com.example.ui.viewmodel.MainViewModel

sealed class Screen {
    object Home : Screen()
    object Categories : Screen()
    object Search : Screen()
    object Favorites : Screen()
    object Profile : Screen()
    data class CategoryDetail(val categoryId: String) : Screen()
    data class ServiceDetail(val serviceId: String) : Screen()
    object Admin : Screen()
}

data class BottomNavItem(
    val screen: Screen,
    val titleHi: String,
    val titleEn: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@Composable
fun AppNavigation(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    var screenStack by remember { mutableStateOf(listOf<Screen>(Screen.Home)) }

    fun navigateTo(screen: Screen) {
        screenStack = screenStack + screen
        currentScreen = screen
    }

    fun navigateBack() {
        if (screenStack.size > 1) {
            val newStack = screenStack.dropLast(1)
            screenStack = newStack
            currentScreen = newStack.last()
        } else {
            // Already at root
            currentScreen = Screen.Home
        }
    }

    // Hardware and gesture BackHandler
    BackHandler(enabled = screenStack.size > 1 || currentScreen != Screen.Home) {
        navigateBack()
    }

    val bottomNavItems = listOf(
        BottomNavItem(
            screen = Screen.Home,
            titleHi = "होम",
            titleEn = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            testTag = "nav_item_home"
        ),
        BottomNavItem(
            screen = Screen.Categories,
            titleHi = "श्रेणियाँ",
            titleEn = "Categories",
            selectedIcon = Icons.Filled.Category,
            unselectedIcon = Icons.Outlined.Category,
            testTag = "nav_item_categories"
        ),
        BottomNavItem(
            screen = Screen.Search,
            titleHi = "खोजें",
            titleEn = "Search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            testTag = "nav_item_search"
        ),
        BottomNavItem(
            screen = Screen.Favorites,
            titleHi = "पसंदीदा",
            titleEn = "Favorites",
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.StarBorder,
            testTag = "nav_item_favorites"
        ),
        BottomNavItem(
            screen = Screen.Profile,
            titleHi = "प्रोफ़ाइल",
            titleEn = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            testTag = "nav_item_profile"
        )
    )

    val isRootTab = currentScreen in listOf(
        Screen.Home,
        Screen.Categories,
        Screen.Search,
        Screen.Favorites,
        Screen.Profile
    )

    Scaffold(
        topBar = {
            if (isRootTab) {
                AppTopHeader(
                    language = language,
                    onToggleLanguage = { viewModel.toggleLanguage() },
                    onAdminClick = { navigateTo(Screen.Admin) }
                )
            }
        },
        bottomBar = {
            if (isRootTab) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    bottomNavItems.forEach { item ->
                        val isSelected = currentScreen == item.screen
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                if (currentScreen != item.screen) {
                                    currentScreen = item.screen
                                    screenStack = listOf(item.screen)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = if (language == AppLanguage.HINDI) item.titleHi else item.titleEn,
                                    modifier = Modifier.testTag(item.testTag)
                                )
                            },
                            label = {
                                Text(
                                    text = if (language == AppLanguage.HINDI) item.titleHi else item.titleEn,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is Screen.Home -> HomeScreen(
                    viewModel = viewModel,
                    onNavigateToSearch = { query ->
                        viewModel.onSearchQueryChanged(query)
                        navigateTo(Screen.Search)
                    },
                    onNavigateToCategory = { catId ->
                        navigateTo(Screen.CategoryDetail(catId))
                    },
                    onNavigateToServiceDetail = { serviceId ->
                        navigateTo(Screen.ServiceDetail(serviceId))
                    }
                )

                is Screen.Categories -> CategoriesScreen(
                    viewModel = viewModel,
                    onCategoryClick = { catId ->
                        navigateTo(Screen.CategoryDetail(catId))
                    }
                )

                is Screen.Search -> SearchScreen(
                    viewModel = viewModel,
                    onServiceClick = { serviceId ->
                        navigateTo(Screen.ServiceDetail(serviceId))
                    }
                )

                is Screen.Favorites -> FavoritesScreen(
                    viewModel = viewModel,
                    onServiceClick = { serviceId ->
                        navigateTo(Screen.ServiceDetail(serviceId))
                    },
                    onExploreServices = {
                        currentScreen = Screen.Categories
                        screenStack = listOf(Screen.Categories)
                    }
                )

                is Screen.Profile -> ProfileScreen(
                    viewModel = viewModel,
                    onNavigateToFavorites = {
                        currentScreen = Screen.Favorites
                        screenStack = listOf(Screen.Favorites)
                    },
                    onNavigateToAdmin = {
                        navigateTo(Screen.Admin)
                    }
                )

                is Screen.CategoryDetail -> CategoryDetailScreen(
                    categoryId = screen.categoryId,
                    viewModel = viewModel,
                    onBackClick = { navigateBack() },
                    onServiceClick = { serviceId ->
                        navigateTo(Screen.ServiceDetail(serviceId))
                    }
                )

                is Screen.ServiceDetail -> ServiceDetailScreen(
                    serviceId = screen.serviceId,
                    viewModel = viewModel,
                    onBackClick = { navigateBack() }
                )

                is Screen.Admin -> AdminScreen(
                    viewModel = viewModel,
                    onBackClick = { navigateBack() }
                )
            }
        }
    }
}
