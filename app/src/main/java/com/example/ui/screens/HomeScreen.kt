package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AppLanguage
import com.example.data.model.ServiceItem
import com.example.ui.components.SafetyBanner
import com.example.ui.components.ServiceCard
import com.example.ui.components.ServiceIcon
import com.example.ui.components.UrlLauncherUtil
import com.example.ui.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigateToSearch: (String) -> Unit,
    onNavigateToCategory: (String) -> Unit,
    onNavigateToServiceDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val language by viewModel.language.collectAsState()
    val allServices by viewModel.allServices.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    val popularServices = allServices.filter { it.isPopular }
    val latestServices = allServices.filter { it.isLatest }
    val biharServices = allServices.filter { it.categoryId == "bihar" }
    val jobServices = allServices.filter { it.categoryId == "jobs" }
    val documentServices = allServices.filter { it.categoryId == "documents" }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Hero Branding Header Box
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                            )
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Prominent Branding Text
                    Text(
                        text = "ANUP Digital",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Surface(
                        color = Color(0xFFFFD54F),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Sarkari Form Apply Center",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D47A1),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "“Rozmarra ke digital kaam — ek hi app se”",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Hero Banner Image
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        shape = RoundedCornerShape(14.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.anup_hero_banner),
                            contentDescription = "Sarkari Digital Services Banner",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Prominent Search Bar
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { onNavigateToSearch("") }
                            .testTag("home_search_bar_clickable"),
                        shape = RoundedCornerShape(14.dp),
                        color = Color.White,
                        shadowElevation = 3.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = if (language == AppLanguage.HINDI) {
                                    "आपको कौन-सा काम करना है? Search करें..."
                                } else {
                                    "What do you want to apply for? Search here..."
                                },
                                fontSize = 13.sp,
                                color = Color(0xFF64748B),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // Safety Advisory Banner
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                SafetyBanner(language = language)
            }
        }

        // Live Announcements Bulletin
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Campaign,
                        contentDescription = "Announcement",
                        tint = Color(0xFFD84315),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = if (language == AppLanguage.HINDI) "नवीनतम सूचनाएँ (Latest Updates)" else "Important Announcements",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(viewModel.announcements) { ann ->
                        Card(
                            modifier = Modifier
                                .width(280.dp)
                                .clickable {
                                    ann.url?.let {
                                        UrlLauncherUtil.openOfficialUrl(context, it, language)
                                    }
                                },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = Color(0xFFE3F2FD),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = ann.tag,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF0D47A1),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Text(
                                        text = ann.date,
                                        fontSize = 10.sp,
                                        color = Color(0xFF94A3B8)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = if (language == AppLanguage.HINDI) ann.titleHindi else ann.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = if (language == AppLanguage.HINDI) ann.messageHindi else ann.message,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Category Icons Grid / Chips
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (language == AppLanguage.HINDI) "त्वरित सेवा श्रेणियाँ (Quick Categories)" else "Quick Categories",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Quick keywords chips: Sarkari Jobs, Bihar Services, Education, Scholarship, Documents, PAN, Voter, Passport, Railway, Government Schemes, Pension, EPFO, Results, Admit Card, ITI, University
                val quickChips = listOf(
                    "Sarkari Jobs" to "jobs",
                    "Bihar Services" to "bihar",
                    "Scholarship" to "education",
                    "PAN Card" to "documents",
                    "Voter ID" to "documents",
                    "Passport" to "documents",
                    "Railway" to "railway",
                    "Government Schemes" to "schemes",
                    "Pension" to "finance",
                    "EPFO" to "finance",
                    "Results" to "results",
                    "Admit Card" to "results",
                    "ITI" to "education",
                    "University" to "education"
                )

                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    quickChips.forEach { (label, catId) ->
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { onNavigateToSearch(label) },
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Text(
                                text = label,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                            )
                        }
                    }
                }
            }
        }

        // Section: Popular Services
        item {
            ServiceSectionHeader(
                title = if (language == AppLanguage.HINDI) "🔥 लोकप्रिय सेवाएँ (Popular Services)" else "🔥 Popular Services",
                onSeeAll = { onNavigateToSearch("") }
            )
        }
        items(popularServices.take(5)) { service ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                ServiceCard(
                    service = service,
                    isFavorite = favoriteIds.contains(service.id),
                    language = language,
                    onServiceClick = { onNavigateToServiceDetail(service.id) },
                    onToggleFavorite = { viewModel.toggleFavorite(service.id) }
                )
            }
        }

        // Section: Bihar Citizen Services
        item {
            ServiceSectionHeader(
                title = if (language == AppLanguage.HINDI) "🌾 बिहार सरकारी सेवाएँ (Bihar RTPS & Land)" else "🌾 Bihar Citizen Services",
                onSeeAll = { onNavigateToCategory("bihar") }
            )
        }
        items(biharServices.take(4)) { service ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                ServiceCard(
                    service = service,
                    isFavorite = favoriteIds.contains(service.id),
                    language = language,
                    onServiceClick = { onNavigateToServiceDetail(service.id) },
                    onToggleFavorite = { viewModel.toggleFavorite(service.id) }
                )
            }
        }

        // Section: Sarkari Jobs & Recruitment
        item {
            ServiceSectionHeader(
                title = if (language == AppLanguage.HINDI) "🏛️ सरकारी नौकरियाँ (Sarkari Jobs & Forms)" else "🏛️ Sarkari Jobs & Recruitment",
                onSeeAll = { onNavigateToCategory("jobs") }
            )
        }
        items(jobServices.take(4)) { service ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                ServiceCard(
                    service = service,
                    isFavorite = favoriteIds.contains(service.id),
                    language = language,
                    onServiceClick = { onNavigateToServiceDetail(service.id) },
                    onToggleFavorite = { viewModel.toggleFavorite(service.id) }
                )
            }
        }

        // Section: Document Services
        item {
            ServiceSectionHeader(
                title = if (language == AppLanguage.HINDI) "📄 दस्तावेज़ सेवाएँ (PAN, Voter, Passport, DL)" else "📄 Document Services",
                onSeeAll = { onNavigateToCategory("documents") }
            )
        }
        items(documentServices.take(4)) { service ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                ServiceCard(
                    service = service,
                    isFavorite = favoriteIds.contains(service.id),
                    language = language,
                    onServiceClick = { onNavigateToServiceDetail(service.id) },
                    onToggleFavorite = { viewModel.toggleFavorite(service.id) }
                )
            }
        }

        // Section: Help & Cyber Cafe Service Center Info
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF1F5F9)
                ),
                border = BorderStroke(1.dp, Color(0xFFCBD5E1))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = if (language == AppLanguage.HINDI) "ANUP Digital सेवा केंद्र सुविधा" else "ANUP Digital Citizen Facilitation",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (language == AppLanguage.HINDI) {
                            "बिहार एवं संपूर्ण भारत के नागरिकों को साइबर कैफ़े व सरकारी फॉर्म अप्लाई की समस्त विश्वसनीय जानकारियाँ व आधिकारिक लिंक्स एक ही स्थान पर उपलब्ध कराना हमारा ध्येय है।"
                        } else {
                            "Providing authentic official links and instructions for Sarkari forms, certificates, scholarships, and digital cyber cafe services across Bihar and India."
                        },
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = Color(0xFF475569)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (language == AppLanguage.HINDI) "100% सत्यापित आधिकारिक सरकारी पोर्टल" else "100% Verified Official Government Portals",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            }
        }

        // Mandatory Footer Text
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ANUP Digital – Sarkari Form Apply Center",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "“Rozmarra ke digital kaam — ek hi app se”",
                    fontSize = 11.sp,
                    color = Color(0xFF64748B)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Made for Citizens of Bihar & India",
                    fontSize = 10.sp,
                    color = Color(0xFF94A3B8)
                )
            }
        }
    }
}

@Composable
fun ServiceSectionHeader(
    title: String,
    onSeeAll: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onSeeAll)
                .padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "See All",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "See All",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}
