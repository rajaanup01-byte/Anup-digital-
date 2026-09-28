package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.data.model.AppLanguage
import com.example.data.model.ServiceItem

object UrlLauncherUtil {

    fun openOfficialUrl(context: Context, url: String, language: AppLanguage) {
        if (url.isBlank()) {
            val msg = if (language == AppLanguage.HINDI) "आधिकारिक लिंक उपलब्ध नहीं है।" else "Official link not available."
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            return
        }

        try {
            var formattedUrl = url.trim()
            if (!formattedUrl.startsWith("http://") && !formattedUrl.startsWith("https://")) {
                formattedUrl = "https://$formattedUrl"
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            val errorMsg = if (language == AppLanguage.HINDI) {
                "Official website currently unavailable. Please try again later."
            } else {
                "Official website currently unavailable. Please try again later."
            }
            Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
        }
    }

    fun shareService(context: Context, service: ServiceItem, language: AppLanguage) {
        val title = if (language == AppLanguage.HINDI) service.nameHindi else service.name
        val desc = if (language == AppLanguage.HINDI) service.descriptionHindi else service.description

        val shareText = """
            🏛️ $title
            📌 $desc
            
            🔗 आधिकारिक वेबसाइट लिंक (Official Link):
            ${service.officialUrl}
            
            ⚠️ सुरक्षा सूचना: केवल official website पर ही personal information और OTP दर्ज करें।
            
            📱 ANUP Digital - Sarkari Form Apply Center
            "Rozmarra ke digital kaam — ek hi app se"
        """.trimIndent()

        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, title)
                putExtra(Intent.EXTRA_TEXT, shareText)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(Intent.createChooser(intent, if (language == AppLanguage.HINDI) "शेयर करें" else "Share via"))
        } catch (e: Exception) {
            Toast.makeText(context, "Error sharing service", Toast.LENGTH_SHORT).show()
        }
    }
}
