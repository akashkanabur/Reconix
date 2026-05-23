package com.reconix.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reconix.ui.components.CyberTopBar
import com.reconix.ui.components.GlassCard
import com.reconix.ui.theme.*

@Composable
fun SettingsScreen(onNavigateBack: () -> Unit, onLogout: () -> Unit) {
    var biometricEnabled by remember { mutableStateOf(true) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var analyticsEnabled by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            containerColor = CardDark,
            title = { Text("Logout", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to logout?", color = TextSecondary) },
            confirmButton = {
                TextButton(onClick = onLogout) { Text("Logout", color = CriticalRed, fontWeight = FontWeight.SemiBold) }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) { Text("Cancel", color = TextSecondary) }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        CyberTopBar("Settings", onNavigateBack)
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsSection("Security") {
                SettingsToggle("Biometric Authentication", Icons.Default.Fingerprint, ElectricBlue, biometricEnabled) { biometricEnabled = it }
                SettingsDivider()
                SettingsItem("API Key Management", Icons.Default.Key, NeonPurple) {}
                SettingsDivider()
                SettingsItem("Session Timeout", Icons.Default.Timer, CyanHighlight) {}
            }
            SettingsSection("Notifications") {
                SettingsToggle("Push Notifications", Icons.Default.Notifications, ElectricBlue, notificationsEnabled) { notificationsEnabled = it }
            }
            SettingsSection("Privacy") {
                SettingsToggle("Analytics", Icons.Default.Analytics, NeonPurple, analyticsEnabled) { analyticsEnabled = it }
                SettingsDivider()
                SettingsItem("Export Data", Icons.Default.Download, ElectricBlue) {}
                SettingsDivider()
                SettingsItem("Delete Account", Icons.Default.DeleteForever, CriticalRed) {}
            }
            SettingsSection("About") {
                SettingsItem("Version 1.0.0", Icons.Default.Info, TextSecondary) {}
                SettingsDivider()
                SettingsItem("Privacy Policy", Icons.Default.Policy, TextSecondary) {}
                SettingsDivider()
                SettingsItem("Terms of Service", Icons.Default.Gavel, TextSecondary) {}
            }
            Spacer(Modifier.height(8.dp))
            OutlinedButton(
                onClick = { showLogoutDialog = true },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = CriticalRed),
                border = androidx.compose.foundation.BorderStroke(1.dp, CriticalRed.copy(alpha = 0.5f))
            ) {
                Icon(Icons.Default.Logout, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Logout", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(title, color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp))
        GlassCard(modifier = Modifier.fillMaxWidth(), glowColor = TextMuted) {
            content()
        }
    }
}

@Composable
private fun SettingsItem(label: String, icon: ImageVector, iconColor: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
        Text(label, color = TextPrimary, fontSize = 15.sp, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
    }
}

@Composable
private fun SettingsToggle(label: String, icon: ImageVector, iconColor: Color, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
        Text(label, color = TextPrimary, fontSize = 15.sp, modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = ElectricBlue, checkedTrackColor = ElectricBlue.copy(alpha = 0.3f))
        )
    }
}

@Composable
private fun SettingsDivider() = HorizontalDivider(color = TextMuted.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 4.dp))
