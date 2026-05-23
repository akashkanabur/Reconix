package com.reconix.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reconix.ui.components.GlassCard
import com.reconix.ui.components.NeonButton
import com.reconix.ui.theme.*

@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToPremium: () -> Unit
) {
    val achievements = listOf(
        "First Blood" to Icons.Default.Star,
        "Bug Hunter" to Icons.Default.BugReport,
        "Recon Master" to Icons.Default.Search,
        "Report Writer" to Icons.Default.Description
    )

    Column(
        modifier = Modifier.fillMaxSize().background(DeepBlack).verticalScroll(rememberScrollState())
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Brush.verticalGradient(listOf(NeonPurple.copy(alpha = 0.3f), DeepBlack)))
        ) {
            IconButton(onClick = onNavigateBack, modifier = Modifier.align(Alignment.TopStart).padding(8.dp)) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
            }
            IconButton(onClick = onNavigateToSettings, modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = TextPrimary)
            }
            Column(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(80.dp).clip(CircleShape)
                        .background(Brush.linearGradient(listOf(ElectricBlue, NeonPurple))),
                    contentAlignment = Alignment.Center
                ) {
                    Text("H", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Spacer(Modifier.height(8.dp))
                Text("HackerPro", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextPrimary)
                Text("hacker@reconix.app", color = TextSecondary, fontSize = 13.sp)
            }
        }

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // XP Bar
            GlassCard(modifier = Modifier.fillMaxWidth(), glowColor = NeonPurple) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Level 7 — Bug Hunter", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Text("2,450 XP", color = NeonPurple, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { 0.65f },
                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                    color = NeonPurple,
                    trackColor = CardDarkElevated
                )
                Spacer(Modifier.height(4.dp))
                Text("650 XP to Level 8", color = TextMuted, fontSize = 12.sp)
            }

            // Stats
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("42" to "Scans", "18" to "Findings", "7" to "Reports").forEach { (value, label) ->
                    GlassCard(modifier = Modifier.weight(1f)) {
                        Text(value, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = ElectricBlue,
                            modifier = Modifier.align(Alignment.CenterHorizontally))
                        Text(label, color = TextSecondary, fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }

            // Achievements
            Text("Achievements", fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                achievements.forEach { (name, icon) ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(48.dp).clip(CircleShape)
                                .background(ElectricBlue.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(icon, contentDescription = name, tint = ElectricBlue, modifier = Modifier.size(24.dp))
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(name, color = TextSecondary, fontSize = 10.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    }
                }
            }

            // Premium CTA
            NeonButton("Upgrade to Premium", onClick = onNavigateToPremium,
                modifier = Modifier.fillMaxWidth(), color = NeonPurple)
        }
    }
}
