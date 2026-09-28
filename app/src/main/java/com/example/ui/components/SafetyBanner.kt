package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage

@Composable
fun SafetyBanner(
    language: AppLanguage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFFF8E1))
            .border(1.dp, Color(0xFFFFD54F), RoundedCornerShape(12.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = "Safety Alert",
                tint = Color(0xFFE65100),
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = if (language == AppLanguage.HINDI) "सुरक्षा निर्देश एवं सूचना" else "Security & Safety Advisory",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFBF360C)
            )
        }

        Text(
            text = if (language == AppLanguage.HINDI) {
                "महत्वपूर्ण: ANUP Digital किसी सरकारी विभाग का official app नहीं है। सरकारी सेवाओं के लिए संबंधित विभाग की official website पर ही आवेदन करें।"
            } else {
                "Important: ANUP Digital is a citizen facilitation directory and not an official government app. Please apply only on official government department websites."
            },
            fontSize = 12.sp,
            lineHeight = 17.sp,
            color = Color(0xFF424242)
        )

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = Color(0xFFD84315),
                modifier = Modifier.size(16.dp).padding(top = 2.dp)
            )
            Text(
                text = if (language == AppLanguage.HINDI) {
                    "OTP, PIN, पासवर्ड, बैंक विवरण या संवेदनशील जानकारी किसी के साथ साझा न करें। केवल आधिकारिक वेबसाइट पर ही विवरण दर्ज करें।"
                } else {
                    "Never share your OTP, PIN, password, or bank details with anyone. Enter personal details strictly on official portals."
                },
                fontSize = 11.sp,
                lineHeight = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFD84315)
            )
        }
    }
}
