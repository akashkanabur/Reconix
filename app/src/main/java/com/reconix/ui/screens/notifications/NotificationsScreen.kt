package com.reconix.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reconix.domain.model.Notification
import com.reconix.domain.model.NotificationType
import com.reconix.ui.components.CyberTopBar
import com.reconix.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NotificationsScreen(onNavigateBack: () -> Unit) {
    val notifications = remember {
        listOf(
            Notification("1", "New Bounty Alert", "Acme Corp increased max bounty to \$10,000", NotificationType.BOUNTY_ALERT, false),
            Notification("2", "Scan Complete", "Subdomain scan for example.com finished — 47 found", NotificationType.SCAN_COMPLETE, false),
            Notification("3", "Program Update", "TechCorp expanded scope to include *.api.techcorp.io", NotificationType.PROGRAM_UPDATE, true),
            Notification("4", "System", "Reconix v1.1 is available with new AI features", NotificationType.SYSTEM, true)
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        CyberTopBar("Notifications", onNavigateBack)
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(notifications) { NotificationItem(it) }
        }
    }
}

@Composable
private fun NotificationItem(notification: Notification) {
    val (icon, color) = when (notification.type) {
        NotificationType.BOUNTY_ALERT -> Icons.Default.MonetizationOn to NeonGreen
        NotificationType.SCAN_COMPLETE -> Icons.Default.CheckCircle to ElectricBlue
        NotificationType.PROGRAM_UPDATE -> Icons.Default.Update to NeonPurple
        NotificationType.SYSTEM -> Icons.Default.Info to CyanHighlight
    }
    val dateFormat = remember { SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (!notification.isRead) CardDarkElevated else CardDark,
                androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier.size(40.dp).clip(CircleShape).background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(notification.title, fontWeight = FontWeight.SemiBold, color = TextPrimary, fontSize = 14.sp)
                Text(dateFormat.format(Date(notification.timestamp)), color = TextMuted, fontSize = 11.sp)
            }
            Spacer(Modifier.height(4.dp))
            Text(notification.message, color = TextSecondary, fontSize = 13.sp)
        }
        if (!notification.isRead) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(ElectricBlue))
        }
    }
}
