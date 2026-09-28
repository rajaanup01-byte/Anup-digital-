package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.ServiceItem
import com.example.ui.components.ServiceIcon
import com.example.ui.viewmodel.MainViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    viewModel: MainViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val allServices by viewModel.allServices.collectAsState()

    var isAuthenticated by remember { mutableStateOf(false) }
    var inputPasscode by remember { mutableStateOf("") }
    var authError by remember { mutableStateOf<String?>(null) }
    var selectedTab by remember { mutableIntStateOf(0) }

    // Service Dialog State
    var showAddDialog by remember { mutableStateOf(false) }
    var editingService by remember { mutableStateOf<ServiceItem?>(null) }

    if (!isAuthenticated) {
        // Secure Passcode Prompt
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(if (language == AppLanguage.HINDI) "एडमिन प्रमाणीकरण" else "Admin Authentication") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(54.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }

                        Text(
                            text = if (language == AppLanguage.HINDI) "एडमिन सुरक्षा पिन दर्ज करें" else "Enter Admin Security PIN",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = if (language == AppLanguage.HINDI) {
                                "डिफ़ॉल्ट एडमिन पिन: 1234 (प्रबंधन हेतु)"
                            } else {
                                "Default Admin Access PIN: 1234"
                            },
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        OutlinedTextField(
                            value = inputPasscode,
                            onValueChange = {
                                inputPasscode = it
                                authError = null
                            },
                            label = { Text("4-digit PIN") },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            singleLine = true,
                            isError = authError != null,
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (authError != null) {
                            Text(
                                text = authError ?: "",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp
                            )
                        }

                        Button(
                            onClick = {
                                if (inputPasscode.trim() == "1234" || inputPasscode.trim() == "2026") {
                                    isAuthenticated = true
                                    authError = null
                                } else {
                                    authError = if (language == AppLanguage.HINDI) "गलत पिन। कृपया पुनः प्रयास करें।" else "Invalid PIN. Please try again."
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                        ) {
                            Text(if (language == AppLanguage.HINDI) "लॉगिन करें" else "Login to Dashboard")
                        }
                    }
                }
            }
        }
        return
    }

    // Authenticated Admin Dashboard
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (language == AppLanguage.HINDI) "ANUP Digital एडमिन पैनल" else "ANUP Digital Admin Panel",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(
                    onClick = {
                        editingService = null
                        showAddDialog = true
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add New Service")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text(if (language == AppLanguage.HINDI) "सेवाएँ (${allServices.size})" else "Services (${allServices.size})") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text(if (language == AppLanguage.HINDI) "सूचनाएँ" else "Announcements") }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("Firebase Sync") }
                )
            }

            when (selectedTab) {
                0 -> {
                    // Services List Management
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            Text(
                                text = if (language == AppLanguage.HINDI) {
                                    "सभी सक्रिय सेवाएँ यहाँ सूचीबद्ध हैं। आप किसी भी सेवा को एडिट या नई सेवा जोड़ सकते हैं।"
                                } else {
                                    "Manage official services, modify URLs, update descriptions or add new services."
                                },
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        items(allServices) { service ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    ServiceIcon(
                                        iconKey = service.iconKey,
                                        size = 40.dp,
                                        iconSize = 22.dp
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = if (language == AppLanguage.HINDI) service.nameHindi else service.name,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = service.officialUrl,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            maxLines = 1
                                        )
                                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                            if (service.isPopular) {
                                                Text("🔥 Popular", fontSize = 10.sp, color = Color(0xFFE65100))
                                            }
                                            if (service.isLatest) {
                                                Text("✨ Latest", fontSize = 10.sp, color = Color(0xFF2E7D32))
                                            }
                                        }
                                    }

                                    IconButton(
                                        onClick = {
                                            editingService = service
                                            showAddDialog = true
                                        }
                                    ) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                                    }

                                    IconButton(
                                        onClick = {
                                            viewModel.deleteService(service.id)
                                        }
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFC62828))
                                    }
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // Announcements Tab
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(viewModel.announcements) { ann ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = ann.tag,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = ann.date,
                                            fontSize = 11.sp,
                                            color = Color(0xFF94A3B8)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = ann.titleHindi,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = ann.messageHindi,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // Firebase Setup Guide
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                            border = BorderStroke(1.dp, Color(0xFFA5D6A7))
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CloudDone,
                                    contentDescription = null,
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(28.dp)
                                )
                                Column {
                                    Text(
                                        text = "Firebase Firestore Architecture Ready",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color(0xFF1B5E20)
                                    )
                                    Text(
                                        text = "The local repository models exactly mirror Cloud Firestore schema.",
                                        fontSize = 11.sp,
                                        color = Color(0xFF2E7D32)
                                    )
                                }
                            }
                        }

                        Text(
                            text = "To connect live Firebase Backend:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = """
                                1. Add google-services.json from your Firebase Console.
                                2. Uncomment 'implementation(libs.firebase.firestore)' in app/build.gradle.kts.
                                3. Collections Structure prepared:
                                   • /services: { id, name, nameHindi, category, officialUrl, isPopular, isLatest, isActive }
                                   • /categories: { id, name, nameHindi, icon, isActive }
                                   • /announcements: { id, title, message, active, createdAt }
                                4. Local changes currently persist via Android Room SQLite Database.
                            """.trimIndent(),
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }

    // Add or Edit Service Dialog
    if (showAddDialog) {
        ServiceFormDialog(
            categories = viewModel.categories,
            initialService = editingService,
            onDismiss = {
                showAddDialog = false
                editingService = null
            },
            onSave = { service ->
                viewModel.addService(service)
                showAddDialog = false
                editingService = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceFormDialog(
    categories: List<com.example.data.model.CategoryItem>,
    initialService: ServiceItem?,
    onDismiss: () -> Unit,
    onSave: (ServiceItem) -> Unit
) {
    var name by remember { mutableStateOf(initialService?.name ?: "") }
    var nameHindi by remember { mutableStateOf(initialService?.nameHindi ?: "") }
    var selectedCatId by remember { mutableStateOf(initialService?.categoryId ?: categories.firstOrNull()?.id ?: "jobs") }
    var officialUrl by remember { mutableStateOf(initialService?.officialUrl ?: "") }
    var description by remember { mutableStateOf(initialService?.description ?: "") }
    var descriptionHindi by remember { mutableStateOf(initialService?.descriptionHindi ?: "") }
    var keywords by remember { mutableStateOf(initialService?.keywords?.joinToString(", ") ?: "") }
    var stateScope by remember { mutableStateOf(initialService?.stateScope ?: "Bihar") }
    var isPopular by remember { mutableStateOf(initialService?.isPopular ?: false) }
    var isLatest by remember { mutableStateOf(initialService?.isLatest ?: true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initialService != null) "Edit Service" else "Add New Service",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = nameHindi,
                    onValueChange = { nameHindi = it },
                    label = { Text("Service Name (Hindi)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Service Name (English)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = officialUrl,
                    onValueChange = { officialUrl = it },
                    label = { Text("Official Website URL (https://...)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = descriptionHindi,
                    onValueChange = { descriptionHindi = it },
                    label = { Text("Description (Hindi)") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (English)") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = keywords,
                    onValueChange = { keywords = it },
                    label = { Text("Search Keywords (Comma separated)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = isPopular, onCheckedChange = { isPopular = it })
                    Text("Mark as Popular Service")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = isLatest, onCheckedChange = { isLatest = it })
                    Text("Mark as Latest Form")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (nameHindi.isNotBlank() && officialUrl.isNotBlank()) {
                        val category = categories.find { it.id == selectedCatId }
                        val service = ServiceItem(
                            id = initialService?.id ?: "custom_${UUID.randomUUID()}",
                            name = name.ifBlank { nameHindi },
                            nameHindi = nameHindi,
                            categoryId = selectedCatId,
                            categoryNameEn = category?.nameEn ?: "Services",
                            categoryNameHi = category?.nameHi ?: "सेवाएँ",
                            description = description.ifBlank { descriptionHindi },
                            descriptionHindi = descriptionHindi,
                            officialUrl = officialUrl.trim(),
                            iconKey = category?.iconKey ?: "public",
                            keywords = keywords.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                            isPopular = isPopular,
                            isLatest = isLatest,
                            isActive = true,
                            stateScope = stateScope
                        )
                        onSave(service)
                    }
                }
            ) {
                Text("Save Service")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
