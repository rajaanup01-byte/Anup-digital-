package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Elderly
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.LocalPostOffice
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ServiceIcon(
    iconKey: String,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    iconSize: Dp = 26.dp,
    containerColor: Color = Color(0xFFE3F2FD),
    tint: Color = Color(0xFF0D47A1),
    isRound: Boolean = true
) {
    val icon: ImageVector = when (iconKey.lowercase()) {
        "work" -> Icons.Default.Work
        "school" -> Icons.Default.School
        "account_balance" -> Icons.Default.AccountBalance
        "badge" -> Icons.Default.Badge
        "train" -> Icons.Default.Train
        "receipt_long" -> Icons.Default.ReceiptLong
        "home" -> Icons.Default.Home
        "landscape" -> Icons.Default.Landscape
        "elderly" -> Icons.Default.Elderly
        "child_care" -> Icons.Default.ChildCare
        "military_tech" -> Icons.Default.MilitaryTech
        "local_post_office" -> Icons.Default.LocalPostOffice
        "gavel" -> Icons.Default.Gavel
        "local_police", "shield" -> Icons.Default.LocalPolice
        "assignment_turned_in" -> Icons.Default.AssignmentTurnedIn
        "credit_card" -> Icons.Default.CreditCard
        "speed" -> Icons.Default.Speed
        "how_to_vote" -> Icons.Default.HowToVote
        "flight_takeoff" -> Icons.Default.FlightTakeoff
        "directions_car" -> Icons.Default.DirectionsCar
        "lock" -> Icons.Default.Lock
        "local_mall" -> Icons.Default.LocalMall
        "confirmation_number" -> Icons.Default.ConfirmationNumber
        "payments", "savings" -> Icons.Default.AccountBalanceWallet
        "agriculture" -> Icons.Default.Agriculture
        "volunteer_activism" -> Icons.Default.VolunteerActivism
        "medical_services" -> Icons.Default.MedicalServices
        "apartment" -> Icons.Default.Apartment
        "description" -> Icons.Default.Description
        else -> Icons.AutoMirrored.Filled.Assignment
    }

    Box(
        modifier = modifier
            .size(size)
            .background(
                color = containerColor,
                shape = if (isRound) CircleShape else RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(iconSize)
        )
    }
}
